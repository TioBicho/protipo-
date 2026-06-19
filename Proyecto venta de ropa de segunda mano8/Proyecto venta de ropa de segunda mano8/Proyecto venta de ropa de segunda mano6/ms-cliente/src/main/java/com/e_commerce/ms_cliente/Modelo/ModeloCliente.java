package com.e_commerce.ms_cliente.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cliente")
public class ModeloCliente {

    @Id
    @JoinColumn(name = "RUT")
    private Long rut;

    @Column(name = "DV", nullable = false)
    private String dv;

    @Column(name = "NOMBRE_CLIENTE", nullable = false, length = 9)
    private String nombreCliente;

    @Column(name = "FECHA_NACIMIENTO", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "PAPELLIDO", nullable = false, length = 15)
    private String papellido;

    @Column(name = "MAPELLIDO", nullable = false, length = 15)
    private String mapellido;

    @Column(name = "PUNTOS", nullable = false)
    private Integer puntos;

    @OneToMany
    @JoinColumn(name = "USUARIO_ID_USUARIO")
    private List<ModeloUsuario> usuarios = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "TIPO_CLIENTE_ID")
    private ModeloTipoCliente tipoCliente;

}