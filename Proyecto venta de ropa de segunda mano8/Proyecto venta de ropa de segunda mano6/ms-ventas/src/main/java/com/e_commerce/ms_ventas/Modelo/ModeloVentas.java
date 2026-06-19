package com.e_commerce.ms_ventas.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ventas")
public class ModeloVentas {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VENTAS_GEN")
    @SequenceGenerator(name = "VENTAS_GEN", sequenceName = "SEQ_VENTAS", allocationSize = 1)
    @Column(name = "id_venta")
    private Long id;

    @Column(nullable = false, length = 30)
    private String boleta;

    @Column(nullable = false)
    private Integer carrito;

    @Column(name = "Cliente_rut", nullable = false)
    private Integer clienteRut;

    @Column(name = "Empleado_id_empleado", nullable = false)
    private Long empleadoId;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModeloDetalleVenta> detalles = new ArrayList<>();
    }