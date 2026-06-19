package com.e_commerce.ms_envios.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "Envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloEnvios {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "envio_seq_gen")
    @SequenceGenerator(name = "envio_seq_gen", sequenceName = "ENVIO_SEQ", allocationSize = 1)
    @Column(name = "id_envio")
    private Long id;

    @Column(name = "direccion_destino", nullable = false, length = 150)
    private String direccionDestino;

    @Column(name = "transportista", nullable = false, length = 50)
    private String transportista;

    @Column(name = "numero_seguimiento", nullable = false, length = 50)
    private String numeroSeguimiento;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "Ventas_id_venta", nullable = false, unique = true)
    private Long ventasIdVenta;
}