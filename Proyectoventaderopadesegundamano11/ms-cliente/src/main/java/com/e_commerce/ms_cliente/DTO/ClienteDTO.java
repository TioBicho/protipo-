package com.e_commerce.ms_cliente.DTO;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    @NotNull(message = "El RUT es obligatorio.")
    private Long rut;

    @NotBlank(message = "El dígito verificador es obligatorio.")
    @Pattern(regexp = "^[0-9kK]{1}$", message = "El DV debe ser un número del 0 al 9 o la letra K.")
    private String dv;

    @NotBlank(message = "El nombre del cliente es obligatorio.")
    @Size(max = 9, message = "El nombre de cliente no puede superar los 9 caracteres.")
    private String nombreCliente;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El apellido paterno es obligatorio.")
    @Size(max = 15, message = "El apellido paterno no puede superar los 15 caracteres.")
    private String papellido;

    @NotBlank(message = "El apellido materno es obligatorio.")
    @Size(max = 15, message = "El apellido materno no puede superar los 15 caracteres.")
    private String mapellido;

    @NotNull(message = "El tipo de cliente es obligatorio.")
    private TipoClienteDTO tipoCliente;
}