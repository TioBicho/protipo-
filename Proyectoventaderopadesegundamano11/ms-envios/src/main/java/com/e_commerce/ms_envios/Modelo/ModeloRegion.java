package com.e_commerce.ms_envios.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Region")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_seq_gen")
    @SequenceGenerator(name = "region_seq_gen", sequenceName = "REGION_SEQ", allocationSize = 1)

    @Column(name = "id_region")
    private Long id;

    @Column(name = "nombre_region", nullable = false, length = 20)
    private String nombreRegion;
}