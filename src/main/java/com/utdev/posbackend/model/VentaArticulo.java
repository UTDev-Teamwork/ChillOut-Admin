package com.utdev.posbackend.model;

import com.utdev.posbackend.model.compositePK.VentaArticuloPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venta_articulo")
public class VentaArticulo implements Serializable {

    @EmbeddedId
    private VentaArticuloPK id_pos;
    @Column
    private String cod_barras;
    @Column
    private short user_code_bascula;
    @Column
    private double cantidad;
    @Column
    private boolean articulo_ofertado;
    @Column
    private double precio_regular;
    @Column
    private boolean cambio_precio;
    @Column
    private double iva;
    @Column
    private double precio_vta;
    @Column
    private double porcent_desc;
    @Column
    private double cant_devuelta;
    @Column
    private String user_name;
    @Column
    private String id_promo;
    @Column
    private short no_promo_aplicado;

}
