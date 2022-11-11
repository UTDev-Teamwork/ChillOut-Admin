package com.utdev.posbackend.model;

import com.utdev.posbackend.model.compositePK.VentaPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "venta")
public class Venta implements Serializable {

    @EmbeddedId
    private VentaPK id_venta;
    @Column
    private String vendedor;
    @Column
    private long folio;
    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;
    @Column
    private double total_vendido;
    @Column
    private double pago_efectivo;
    @Column
    private double pago_cheque;
    @Column
    private double pago_vales;
    @Column
    private double pago_tc;
    @Column
    private String supervisor;
    @Column
    private boolean upload;
    @Column
    private short num_registros;
    @Column
    private double numeric;

}
