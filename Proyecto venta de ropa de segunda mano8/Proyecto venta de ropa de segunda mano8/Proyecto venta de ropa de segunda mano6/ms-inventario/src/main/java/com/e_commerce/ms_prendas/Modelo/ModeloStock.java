package com.e_commerce.ms_prendas.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloStock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_seq_gen")
    @SequenceGenerator(name = "stock_seq_gen", sequenceName = "STOCK_SEQ", allocationSize = 1)
    @Column(name = "id_stock")
    private Long id;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "estado_inventario", nullable = false, length = 50)
    private String estadoInventario;


    @Column(name = "Ropa_id_ropa", nullable = false, length = 8)
    private String ropaIdRopa;

    @Column(name = "Sucursal_id_sucursal", nullable = false)
    private Long sucursalIdSucursal;
}