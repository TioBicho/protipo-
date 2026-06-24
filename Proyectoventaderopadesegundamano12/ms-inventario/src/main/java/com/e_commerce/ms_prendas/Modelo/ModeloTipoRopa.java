package com.e_commerce.ms_prendas.Modelo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tipo_ropa")
public class ModeloTipoRopa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOROPA_GEN")
    @SequenceGenerator(name = "TIPOROPA_GEN", sequenceName = "SEQ_TIPO_ROPA", allocationSize = 1)
    @Column(name = "id_ropa")
    private Long id;

    @Column(name = "diseno", length = 1, columnDefinition = "NVARCHAR2(1)")
    private String diseno;

    @Column(name = "estilo", length = 15, columnDefinition = "VARCHAR2(15)")
    private String estilo;

    @Column(name = "color", length = 20, columnDefinition = "VARCHAR2(20)")
    private String color;

    @Column(name = "composicion", length = 50, columnDefinition = "VARCHAR2(50)")
    private String composicion;

    @Column(name = "detalles", length = 20, columnDefinition = "VARCHAR2(20)")
    private String detalles;

    @Column(name = "tipo_prenda", length = 30, columnDefinition = "VARCHAR2(30)")
    private String tipoPrenda;

    @Column(name = "genero", length = 8, columnDefinition = "VARCHAR2(8)")
    private String genero;

    @Column(name = "talla", length = 15, columnDefinition = "VARCHAR2(15)")
    private String talla;

    @Column(name = "marca", length = 30, columnDefinition = "NVARCHAR2(30)")
    private String marca;

    @Column(name = "estado_prenda", length = 18, columnDefinition = "VARCHAR2(18)")
    private String estadoPrenda;

    @Column(name = "Categoria_id_categoria", nullable = false)
    private Long categoriaId;
}