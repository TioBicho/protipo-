package com.e_commerce.ms_sucursal.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class sucursalDTO {

    @NotBlank(message = "El nombre de la sucursal es obligatorio.")
    @Size(max = 15, message = "El nombre de la sucursal no puede superar los 15 caracteres.")
    private String nombreSucursal;

    @NotBlank(message = "La ciudad de la sucursal es obligatoria.")
    @Size(max = 20, message = "La ciudad no puede superar los 20 caracteres.")
    private String ciudad;

    @NotNull(message = "El ID de la región es obligatorio.")
    private Long regionId;
}