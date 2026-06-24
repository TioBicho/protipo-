package com.e_commerce.ms_prendas.Modelo;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ropa")
public class ModeloPrenda {

    @Id
    @Column(name = "id_ropa", length = 8, columnDefinition = "NVARCHAR2(8)")
    private String id;

    @Column(name = "cuidados", length = 80, columnDefinition = "VARCHAR2(80)")
    private String cuidados;

    @Column(name = "descripcion", nullable = false, length = 80, columnDefinition = "NVARCHAR2(80)")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "Tipo_ropa_id_ropa", nullable = false)
    private ModeloTipoRopa tipoRopa;
}
