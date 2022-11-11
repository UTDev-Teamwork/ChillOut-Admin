package com.utdev.posbackend.model.compositePK;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaArticuloPK implements Serializable {

    private int id_pos;
    private String id_venta;
    private long no_articulo;

}
