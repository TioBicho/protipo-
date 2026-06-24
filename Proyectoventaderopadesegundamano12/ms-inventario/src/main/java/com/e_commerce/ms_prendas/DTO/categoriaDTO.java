package com.e_commerce.ms_prendas.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class categoriaDTO {

    @NotBlank(message = "El nombre de la categoría es obligatorio.")
    @Size(max = 50, message = "El nombre de la categoría no puede superar los 50 caracteres.")

    private String nombreCategoria;
}