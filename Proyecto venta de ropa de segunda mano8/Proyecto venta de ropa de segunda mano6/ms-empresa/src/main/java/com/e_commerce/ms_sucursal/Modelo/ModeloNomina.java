package com.e_commerce.ms_sucursal.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "Nomina")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloNomina {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nomina_seq_gen")
    @SequenceGenerator(name = "nomina_seq_gen", sequenceName = "NOMINA_SEQ", allocationSize = 1)
    @Column(name = "id_nomina")
    private Long id;

    @Column(name = "sueldo_base", nullable = false)
    private Long sueldoBase;

    @Column(name = "bonos")
    private Long bonos;

    @Column(name = "descuentos_legales", nullable = false)
    private Long descuentos;

    @Column(name = "sueldo_liquido", nullable = false)
    private Long sueldoLiquido;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;


    @Column(name = "empleado_id_empleado", nullable = false)
    private Long empleadoId;
}