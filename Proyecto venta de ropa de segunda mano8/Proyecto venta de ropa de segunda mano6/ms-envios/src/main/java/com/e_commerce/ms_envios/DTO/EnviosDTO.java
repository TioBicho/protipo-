package com.e_commerce.ms_envios.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnviosDTO {

    @NotBlank(message = "La dirección de destino es obligatoria.")
    @Size(max = 150, message = "La dirección de destino no puede superar los 150 caracteres.")
    private String direccionDestino;

    @NotBlank(message = "El nombre del transportista es obligatorio.")
    @Size(max = 50, message = "El transportista no puede superar los 50 caracteres.")
    private String transportista;

    @NotBlank(message = "El número de seguimiento es obligatorio.")
    @Size(max = 50, message = "El número de seguimiento no puede superar los 50 caracteres.")
    private String numeroSeguimiento;

    @NotBlank(message = "El estado del envío es obligatorio.")
    @Size(max = 50, message = "El estado del envío no puede superar los 50 caracteres.")
    private String estado;

    @NotNull(message = "El ID de la venta asociada es obligatorio.")
    private Long ventasIdVenta;
}