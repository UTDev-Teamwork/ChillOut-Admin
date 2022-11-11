package com.utdev.posbackend.controller;

import com.utdev.posbackend.model.Articulo;
import com.utdev.posbackend.service.IArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
@RequestMapping("api/articulo")
public class ArticuloController {

    @Autowired
    private IArticuloService IArticuloService;

    /************************************** READ **********************************************/
    // http://localhost:7373/api/articulo/all
    @GetMapping("/all")
    public ResponseEntity<?> getAllArticulos()
    {
        return ResponseEntity.ok(IArticuloService.findAllArticulos());
    }

    // http://localhost:7373/api/articulo/top10
    @GetMapping("/top10")
    public ResponseEntity<?> get10Articulos()
    {
        return ResponseEntity.ok(IArticuloService.findTenArticulos());
    }

    // http://localhost:7373/api/articulo/700000000000001
    @GetMapping("{codBarras}")
    public ResponseEntity<?> getArticuloById(@PathVariable String codBarras){
        return ResponseEntity.ok(IArticuloService.findById(codBarras));
    }

    // http://localhost:7373/api/articulo?des=Ajonjoli&page=2&size=3
    @GetMapping
    public ResponseEntity<?> getArticulos(
            @RequestParam(required = false) String des,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try {
            List<Articulo> articulos = new ArrayList<Articulo>();
            Pageable paging = PageRequest.of(page, size);
            Page<Articulo> pageArticulos;

            if(des == null)
                pageArticulos = IArticuloService.findPaging(paging);
            else
                pageArticulos = IArticuloService.findByNombre(des, paging);

            articulos = pageArticulos.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("articulos", articulos);
            response.put("currentPage", pageArticulos.getNumber());
            response.put("totalItems", pageArticulos.getTotalElements());
            response.put("totalPages", pageArticulos.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // http://localhost:7373/api/articulo/img?page=2&size=3
    @GetMapping("/img")
    public ResponseEntity<?> getArticulosWithImg(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try {
            List<Articulo> articulos = new ArrayList<Articulo>();
            Pageable paging = PageRequest.of(page, size);

            Page<Articulo> pageArticulos = IArticuloService.findByImage(true, paging);
            articulos = pageArticulos.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("articulos", articulos);
            response.put("currentPage", pageArticulos.getNumber());
            response.put("totalItems", pageArticulos.getTotalElements());
            response.put("totalPages", pageArticulos.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /************************************** CREATE & UPDATE **********************************************/
    // http://localhost:7373/api/articulo
    @PostMapping
    public ResponseEntity<?> saveArticulo(@RequestBody Articulo articulo)
    {
        return new ResponseEntity<>(
                IArticuloService.saveArticulo(articulo),
                HttpStatus.CREATED
        );
    }

    /************************************** DELETE **********************************************/
    // http://localhost:7373/api/danger-delete/700000000000001
    @DeleteMapping("/danger-delete/{articuloId}")
    public ResponseEntity<?> deleteArticulo(@PathVariable String articuloId)
    {
        IArticuloService.deleteArticulo(articuloId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:7373/api/articulo/suspend/700000000000001
    @DeleteMapping("/suspend/{articuloId}")
    public ResponseEntity<?> suspendArticulo(@PathVariable String articuloId)
    {
        return IArticuloService.suspendArticulo(articuloId)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    // http://localhost:7373/api/articulo/suspend/700000000000001
    @DeleteMapping("/cancel/{articuloId}")
    public ResponseEntity<?> cancelArticulo(@PathVariable String articuloId)
    {
        return IArticuloService.cancelArticulo(articuloId)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
