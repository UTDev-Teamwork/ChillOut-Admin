package com.utdev.posbackend.service;

import com.utdev.posbackend.model.VentaArticulo;
import com.utdev.posbackend.model.compositePK.VentaArticuloPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVentaArticuloService {
    // CREATE & UPDATE
    VentaArticulo saveVentaArticulo(VentaArticulo venta);

    VentaArticulo createVentaArticulo(String idVenta, int noArt, String codBarras, double cantidad);

    List<VentaArticulo> findAll();

    VentaArticulo findById(VentaArticuloPK id);

    Page<VentaArticulo> findTopTen();

    Page<VentaArticulo> findPaging(Pageable page);

    // DELETE
    void deleteVentaArticulo(VentaArticuloPK id);
}
