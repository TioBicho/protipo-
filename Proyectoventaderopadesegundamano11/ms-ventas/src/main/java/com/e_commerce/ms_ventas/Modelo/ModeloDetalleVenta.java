package com.e_commerce.ms_ventas.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Detalle_venta")
public class ModeloDetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DETALLE_VENTA_GEN")
    @SequenceGenerator(name = "DETALLE_VENTA_GEN", sequenceName = "SEQ_DETALLE_VENTA", allocationSize = 1)
    @Column(name = "id_detalle_venta")
    private Long id;

    @Column(name = "precio_original", nullable = false)
    private Integer precioOriginal;

    @Column(name = "descuento_aplicado", nullable = false)
    private Integer descuentoAplicado;

    @Column(name = "ropa_id_ropa", length = 8, columnDefinition = "NVARCHAR2(8)")
    private String ropaId;

    @ManyToOne
    @JoinColumn(name = "Ventas_id_venta")
    private ModeloVentas venta;
}