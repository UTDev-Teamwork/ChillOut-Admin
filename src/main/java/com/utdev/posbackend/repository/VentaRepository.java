package com.utdev.posbackend.repository;

import com.utdev.posbackend.model.ArticuloCantidad;
import com.utdev.posbackend.model.Venta;
import com.utdev.posbackend.model.compositePK.VentaPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, VentaPK> {

    Page<Venta> findByFolio(long folio, Pageable pageable);

    Page<Venta> findByFechaVenta(LocalDateTime fecha, Pageable pageable);

    @Query(value = "SELECT cod_barras as codBarras, SUM(cantidad) as cantidad FROM pos_admin.venta_articulo GROUP BY cod_barras ORDER BY cantidad DESC LIMIT 10;",
            nativeQuery = true)
    List<Object[]> findTopTenArticles();

}
