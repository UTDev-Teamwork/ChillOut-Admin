package com.utdev.posbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="articulo_imagen")
public class Imagen {

    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String type;

    @Lob
    @Column
    private byte[] img;

    @Column
    private String articulo_id;

}
