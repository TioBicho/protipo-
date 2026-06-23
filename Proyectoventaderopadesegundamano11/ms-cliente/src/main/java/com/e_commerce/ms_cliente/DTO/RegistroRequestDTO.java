package com.e_commerce.ms_cliente.DTO;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class RegistroRequestDTO {

    @Valid
    private ClienteDTO cliente;

    @Valid
    private UsuarioDTO usuario;
    @Valid
    private PerfilDTO perfil;
}