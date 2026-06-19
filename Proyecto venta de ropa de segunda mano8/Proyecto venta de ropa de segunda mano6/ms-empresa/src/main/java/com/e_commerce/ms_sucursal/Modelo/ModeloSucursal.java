package com.e_commerce.ms_sucursal.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUCURSAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloSucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sucursal_seq_gen")
    @SequenceGenerator(name = "sucursal_seq_gen", sequenceName = "SUCURSAL_SEQ", allocationSize = 1)
    @Column(name = "id_sucursal")
    private Long id;


    @Column(name = "nombre_sucursal", nullable = false, length = 15)
    private String nombreSucursal;


    @Column(name = "ciudad", nullable = false, length = 20)
    private String ciudad;

    @Column(name = "Region_id_region", nullable = false)
    private Long regionId;
}