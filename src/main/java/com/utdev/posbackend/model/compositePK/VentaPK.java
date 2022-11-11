package com.utdev.posbackend.model.compositePK;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaPK implements Serializable {

    private int id_pos;
    private String id_venta;

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id_pos);
        hash = 59 * hash + Objects.hashCode(this.id_venta);
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        final VentaPK other = (VentaPK) obj;

        if (!Objects.equals(this.id_venta, other.id_venta))
            return false;
        if (!Objects.equals(this.id_pos, other.id_pos))
            return false;

        return true;
    }

}
