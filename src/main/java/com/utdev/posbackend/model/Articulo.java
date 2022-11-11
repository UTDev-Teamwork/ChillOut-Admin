package com.utdev.posbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// https://tada.github.io/pljava/use/datetime.html
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="articulo")
public class Articulo {

    @Id
    private String cod_barras;                  // Max 15 / NOT NULL / PK / (700000000000001)
    @Column
    private String cod_asociado;                // Max 15 / (NULL)
    @Column
    private long id_clasificacion;              // NOT NULL / TABLA EXTERNA / (1711)
    @Column
    private String cod_interno;                 // Max 15 / (NULL)
    @Column
    private String descripcion;                 // Max 80 / NOT NULL / (Rifate una descripcion completa)
    @Column
    private String descripcion_corta;           // Max 30 / NOT NULL / ('Que,Como,Sexo,Color' - Tienda)
    @Column
    private double cantidad_um;                 // Max 19 / Scale 3 / NOT NULL / (1.0)
    @Column
    private String id_unidad;                      // Max 36 / NOT NULL / TABLA EXTERNA (8C383587-7684-4961-BEE4-AD0B45A49D4C)
    @Column
    private String id_proveedor;                   // Max 36 / NOT NULL / TABLA EXTERNA (744514AE-5C17-400B-AE64-2202FF1AD60B)
    @Column
    private double precio_compra;               // Max 19 / Scale 3 / NOT NULL (265.50)
    @Column
    private double utilidad;                    // Max 19 / Scale 3 / NOT NULL (364.50)
    @Column
    private double precio_venta;                // Max 19 / Scale 3 / NOT NULL / (630.00)
    @Column
    private String tipo_articulo;               // Max 50 / NOT NULL / (principal)
    @Column
    private double stock;                       // Max 19 / Scale 3 / NOT NULL / (10.0)
    @Column
    private double stock_min;                   // Max 19 / Scale 3 / NOT NULL / (0.0)
    @Column
    private double stock_max;                   // Max 19 / Scale 3 / NOT NULL / (0.0)
    @Column
    private double iva;                         // Max 4 / Scale 2 / NOT NULL / (0.16)
    @Column
    private LocalDateTime kit_fecha_ini;        // Max 3 / Timestamp without time zone / NOT NULL / (NULL)
    @Column
    private LocalDateTime kit_fecha_fin;        // Max 3 / Timestamp without time zone / NOT NULL / (NULL)
    @Column
    private boolean articulo_disponible;        // Boolean / NOT NULL / (true)
    @Column
    private boolean kit;                        // Boolean / NOT NULL / (false)
    @Column
    private LocalDateTime fecha_registro;       // Max 3 / Timestamp without time zone / NOT NULL / (LocalDateTime.now())
    @Column
    private boolean visible;                    // Boolean / NOT NULL / (true)
    @Column
    private short puntos;                       // Smallint / NOT NULL / (0)
    @Column
    private LocalDateTime last_update_inventory;// Max 3 / Timestamp without time zone / NOT NULL / (LocalDateTime.now())
    @Column
    private String cve_producto;                // Max 15 / NOT NULL / (0)
    @Column()
    private boolean hasimg;                    // Boolean / (true)
}
