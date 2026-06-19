package com.e_commerce.ms_prendas.DTO;

import com.e_commerce.ms_prendas.DTO.tipoRopaDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PrendaDTO {

    @NotBlank(message = "El SKU es obligatorio")
    @Size(max = 8, message = "El SKU no puede tener más de 8 caracteres")
    private String id;

    @Size(max = 100, message = "La descripción no puede superar los 100 caracteres")
    private String descripcion;

    @NotBlank(message = "Las instrucciones de cuidado son obligatorias")
    @Size(max = 50, message = "El texto de cuidados no puede superar los 50 caracteres")
    private String cuidados;

    private tipoRopaDTO tipoRopa;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class categoriaDTO {

        @NotBlank(message = "El nombre de la categoría es obligatorio.")
        @Size(max = 50, message = "El nombre de la categoría no puede superar los 50 caracteres.")

        private String nombreCategoria;
    }
}