package com.utdev.posbackend.service;

import com.utdev.posbackend.model.ArticuloCantidad;
import com.utdev.posbackend.model.Venta;
import com.utdev.posbackend.model.compositePK.VentaPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IVentaService {

    // CREATE & UPDATE
    Venta saveVenta(Venta venta);

    // CREATE & UPDATE
    Venta saveVenta(String id, double total, int productos);

    // READ
    List<Venta> findAllVentas();

    Venta findById(VentaPK id);

    Page<Venta> findTopTen();

    List<ArticuloCantidad> findTopTenArticles();

    Page<Venta> findPaging(Pageable pageable);

    Page<Venta> findByFolio(long folio, Pageable pageable);

    Page<Venta> findByFecha(LocalDateTime fecha, Pageable pageable);

    // DELETE
    void deleteVenta(VentaPK ventaPK);
}
