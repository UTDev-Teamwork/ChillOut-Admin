package com.utdev.posbackend.repository;

import com.utdev.posbackend.model.Articulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo,String> {

    Page<Articulo> findByHasimg(boolean image, Pageable pageable);
    Page<Articulo> findByDescripcionContaining(String descripcion, Pageable pageable);

}
