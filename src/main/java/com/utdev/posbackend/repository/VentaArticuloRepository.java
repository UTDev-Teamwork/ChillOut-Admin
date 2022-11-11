package com.utdev.posbackend.repository;

import com.utdev.posbackend.model.VentaArticulo;
import com.utdev.posbackend.model.compositePK.VentaArticuloPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaArticuloRepository extends JpaRepository<VentaArticulo, VentaArticuloPK> {
}
