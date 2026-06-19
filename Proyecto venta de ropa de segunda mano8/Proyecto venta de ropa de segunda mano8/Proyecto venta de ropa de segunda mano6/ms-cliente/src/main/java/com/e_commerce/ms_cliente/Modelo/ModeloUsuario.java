package com.e_commerce.ms_cliente.Modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq_gen")
    @SequenceGenerator(name = "usuario_seq_gen", sequenceName = "USUARIO_SEQ", allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "password", nullable = false, length = 14)
    private String password;

    @Column(name = "rol", nullable = false, length = 15)
    private String rol;

    @OneToOne
    @JoinColumn(name = "Perfil_id_perfil", nullable = false, unique = true)
    private ModeloPerfil perfil;
}