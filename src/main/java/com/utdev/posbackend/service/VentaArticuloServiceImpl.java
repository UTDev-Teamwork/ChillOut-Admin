package com.utdev.posbackend.service;

import com.utdev.posbackend.model.Articulo;
import com.utdev.posbackend.model.VentaArticulo;
import com.utdev.posbackend.model.compositePK.VentaArticuloPK;
import com.utdev.posbackend.repository.VentaArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaArticuloServiceImpl implements  IVentaArticuloService{

    @Autowired
    private IArticuloService articuloService;
    private final VentaArticuloRepository ventaArticuloRepository;

    public VentaArticuloServiceImpl(VentaArticuloRepository ventaArticuloRepository) {
        this.ventaArticuloRepository = ventaArticuloRepository;
    }

    // CREATE & UPDATE
    @Override
    public VentaArticulo saveVentaArticulo(VentaArticulo venta){
        Articulo articulo = articuloService.findById(venta.getCod_barras());
        if(articulo==null)
            return null;
        VentaArticulo va = ventaArticuloRepository.save(venta);
        if(va!=null){
            articulo.setStock(articulo.getStock()-venta.getCantidad());
            articulo.setLast_update_inventory(LocalDateTime.now());
            articuloService.saveArticulo(articulo);
        }
        return va;
    }

    @Override
    public VentaArticulo createVentaArticulo(String idVenta, int noArt, String codBarras, double cantidad){
        //System.out.println("Pasito1");
        Articulo articulo = articuloService.findById(codBarras);
        //System.out.println("Pasito2");
        if(articulo==null)
            return null;
        //System.out.println("Pasito3");
        return new VentaArticulo(
            new VentaArticuloPK(1,idVenta,noArt),
            articulo.getCod_barras(),
            (short) 1,
            cantidad,
            false,
            articulo.getPrecio_venta(),
            false,
            articulo.getIva(),
            articulo.getPrecio_venta(),
            0.00,
            0.00,
            "ClienteReact",
            "1",
            (short) 0
        );
    }

    @Override
    public List<VentaArticulo> findAll(){ return ventaArticuloRepository.findAll(); }

    @Override
    public VentaArticulo findById(VentaArticuloPK id){ return ventaArticuloRepository.findById(id).orElse(null); }

    @Override
    public Page<VentaArticulo> findTopTen(){
        Pageable limit = PageRequest.of(0,10);
        return ventaArticuloRepository.findAll(limit);
    }

    @Override
    public Page<VentaArticulo> findPaging(Pageable page){ return ventaArticuloRepository.findAll(page); }

    // DELETE
    @Override
    public void deleteVentaArticulo(VentaArticuloPK id){ ventaArticuloRepository.deleteById(id); }

}
