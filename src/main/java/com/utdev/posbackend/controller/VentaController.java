package com.utdev.posbackend.controller;

import com.utdev.posbackend.model.ArticuloCantidad;
import com.utdev.posbackend.model.Venta;
import com.utdev.posbackend.model.VentaArticulo;
import com.utdev.posbackend.model.compositePK.VentaPK;
import com.utdev.posbackend.service.IVentaArticuloService;
import com.utdev.posbackend.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
@RequestMapping("api/venta")
public class VentaController {

    @Autowired
    private IVentaService ventaService;
    @Autowired
    private IVentaArticuloService ventaArticuloService;

    /************************************** READ **********************************************/
    // http://localhost:7373/api/venta/all
    @GetMapping("/all")
    public ResponseEntity<?> getAllVentas(){ return ResponseEntity.ok(ventaService.findAllVentas()); }

    // http://localhost:7373/api/venta/top10
    @GetMapping("/top10")
    public ResponseEntity<?> get10Ventas(){ return ResponseEntity.ok(ventaService.findTopTen()); }

    // http://localhost:7373/api/venta/d24fc6b6-5346-11ed-bdc3-0242ac120002
    @GetMapping("{idVenta}")
    public ResponseEntity<?> getVentaById(@PathVariable String idVenta){
        return ResponseEntity.ok(ventaService.findById(new VentaPK(1, idVenta)));
    }

    // http://localhost:7373/api/venta?page=0&size=10
    @GetMapping
    public ResponseEntity<?> getVentasByFolio(
            @RequestParam(required = false) String folio,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try {
            List<Venta> ventas = new ArrayList<Venta>();
            Pageable paging = PageRequest.of(page, size);
            Page<Venta> pageVentas;

            if(folio == null)
                pageVentas = ventaService.findPaging(paging);
            else {
                try {
                    int folioInt;
                    folioInt = Integer.parseInt(folio);
                    pageVentas = ventaService.findByFolio(folioInt, paging);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            ventas = pageVentas.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("ventas", ventas);
            response.put("currentPage", pageVentas.getNumber());
            response.put("totalItems", pageVentas.getTotalElements());
            response.put("totalPages", pageVentas.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:7373/api/venta/art?page=0&size=10
    @GetMapping("/art")
    public ResponseEntity<?> getVentasArticulo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try {
            List<VentaArticulo> ventas = new ArrayList<VentaArticulo>();
            Pageable paging = PageRequest.of(page, size);
            Page<VentaArticulo> pageVentas;

            pageVentas = ventaArticuloService.findPaging(paging);

            ventas = pageVentas.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("ventas", ventas);
            response.put("currentPage", pageVentas.getNumber());
            response.put("totalItems", pageVentas.getTotalElements());
            response.put("totalPages", pageVentas.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/toptenarticles")
    public ResponseEntity<?> getTopTenArticles(){
        return new ResponseEntity<>(ventaService.findTopTenArticles(), HttpStatus.OK);
    }

    /************************************** CREATE & UPDATE **********************************************/
    // http://localhost:7373/api/venta
    @PostMapping
    public ResponseEntity<?> saveVenta(@RequestBody List<ArticuloCantidad> onlineCart){
        try {
            List<VentaArticulo> ventaArticulos = new ArrayList<VentaArticulo>();

            String idVenta = UUID.randomUUID().toString();
            int i = 0;
            double total = 0.0;

            for(ArticuloCantidad cart: onlineCart){

                System.out.println(cart);
                VentaArticulo va = ventaArticuloService.createVentaArticulo(idVenta, ++i, cart.getCodBarras(), cart.getCantidad());

                if(va != null){
                    ventaArticulos.add(va);
                    total += va.getPrecio_regular()*va.getCantidad();
                }
            }

            Venta venta = ventaService.saveVenta(idVenta, total,i);

            if (venta != null){
                int productos = 0;
                for(VentaArticulo ventaArticulo: ventaArticulos){
                    if(ventaArticuloService.saveVentaArticulo(ventaArticulo)!=null){
                        productos++;
                    }
                }

                if(productos != ventaArticulos.size())
                    return new ResponseEntity<>("No se agregaron todas las ventas-articulo",HttpStatus.OK);

                venta.setUpload(true);
                if(ventaService.saveVenta(venta)!=null)
                    return new ResponseEntity<>(HttpStatus.OK);
                return new ResponseEntity<>("No se pudo dar de alta la venta",HttpStatus.NOT_MODIFIED);
            } else {
                return new ResponseEntity<>("No se pudo dar de alta la venta",HttpStatus.NOT_MODIFIED);
            }
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
