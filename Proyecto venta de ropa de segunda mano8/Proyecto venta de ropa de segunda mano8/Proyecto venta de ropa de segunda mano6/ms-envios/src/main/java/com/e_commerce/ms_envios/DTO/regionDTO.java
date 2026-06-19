package com.e_commerce.ms_envios.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class regionDTO {

    @NotBlank(message = "El nombre de la región es obligatorio.")
    @Size(max = 20, message = "El nombre de la región no puede superar los 20 caracteres.")
    private String nombreRegion;
}