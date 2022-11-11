package com.utdev.posbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ArticuloCantidad {

    private String codBarras;
    private int cantidad;

}
