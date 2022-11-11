package com.utdev.posbackend.service;

import com.utdev.posbackend.model.Articulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IArticuloService {
    Articulo saveArticulo(Articulo articulo);

    Articulo findById(String id);

    Page<Articulo> findPaging(Pageable pageable);

    Page<Articulo> findByNombre(String nombre, Pageable pageable);

    Page<Articulo> findByImage(boolean img, Pageable pageable);

    void deleteArticulo(String codBarras);

    boolean cancelArticulo(String codBarras);

    boolean suspendArticulo(String codBarras);

    List<Articulo> findAllArticulos();

    Page<Articulo> findTenArticulos();
}
