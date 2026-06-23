package com.e_commerce.ms_prendas.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categoria")
public class ModeloCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORIA_GEN")
    @SequenceGenerator(name = "CATEGORIA_GEN", sequenceName = "SEQ_CATEGORIA", allocationSize = 1)
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "nombre_categoria", nullable = false, length = 50)
    private String nombre;

}