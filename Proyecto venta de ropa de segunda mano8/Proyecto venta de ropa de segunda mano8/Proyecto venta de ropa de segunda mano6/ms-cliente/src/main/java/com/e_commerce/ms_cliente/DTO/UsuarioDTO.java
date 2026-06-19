package com.e_commerce.ms_cliente.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Size(max = 30, message = "El correo electrónico no puede superar los 30 caracteres.")
    @Email(message = "El formato del correo electrónico no es válido.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El correo debe incluir un '@' y un dominio válido con punto (ejemplo: nombre@correo.cl).")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 8, max = 14, message = "La contraseña debe tener entre 8 y 14 caracteres.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).*$",
            message = "La contraseña debe contener al menos un número, una letra mayúscula y una minúscula.")
    private String password;

    @NotBlank(message = "El rol del usuario es obligatorio.")
    @Size(max = 15, message = "El rol no puede superar los 15 caracteres.")
    @Pattern(regexp = "CLIENTE|EMPLEADO|ADMIN", message = "El rol debe ser CLIENTE, EMPLEADO o ADMIN")
    private String rol;


}