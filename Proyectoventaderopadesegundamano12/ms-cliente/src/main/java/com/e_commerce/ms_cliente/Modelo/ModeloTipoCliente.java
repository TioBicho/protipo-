package com.e_commerce.ms_cliente.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TIPO_CLIENTE")
public class ModeloTipoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_cliente_gen")
    @SequenceGenerator(name = "tipo_cliente_gen", sequenceName = "SEQ_TIPO_CLIENTE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CATEGORIA_CLIENTE", nullable = false, length = 8)
    private String categoriaCliente;

    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro; // DATE

    @Column(name = "PUNTOS_MINIMOS", nullable = false)
    private Integer puntosMinimos;
}