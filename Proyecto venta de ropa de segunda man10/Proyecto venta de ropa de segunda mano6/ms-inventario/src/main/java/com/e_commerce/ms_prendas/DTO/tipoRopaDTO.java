package com.e_commerce.ms_prendas.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class tipoRopaDTO {

    private Long id;

    @Size(max = 1, message = "El diseño no puede superar 1 carácter")
    private String diseno;

    @Size(max = 15, message = "El estilo no puede superar los 15 caracteres")
    private String estilo;

    @Size(max = 20, message = "El color no puede superar los 20 caracteres")
    private String color;

    @Size(max = 50, message = "La composición no puede superar los 50 caracteres")
    private String composicion;

    @Size(max = 20, message = "Los detalles no pueden superar los 20 caracteres")
    private String detalles;

    @Size(max = 30, message = "El tipo de prenda no puede superar los 30 caracteres")
    private String tipoPrenda;

    @Size(max = 8, message = "El género no puede superar los 8 caracteres")
    private String genero;

    @Size(max = 15, message = "La talla no puede superar los 15 caracteres")
    private String talla;

    @Size(max = 30, message = "La marca no puede superar los 30 caracteres")
    private String marca;

    @Size(max = 18, message = "El estado de la prenda no puede superar los 18 caracteres")
    private String estadoPrenda;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long categoriaId;
}