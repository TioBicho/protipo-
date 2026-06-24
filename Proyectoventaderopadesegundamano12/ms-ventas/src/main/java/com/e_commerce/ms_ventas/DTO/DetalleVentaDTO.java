package com.e_commerce.ms_ventas.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DetalleVentaDTO {

    @NotNull(message = "El precio original es obligatorio")
    @Min(value = 0, message = "El precio original no puede ser un valor negativo")
    private Integer precioOriginal;

    @NotNull(message = "El descuento aplicado es obligatorio")
    @Min(value = 0, message = "El descuento aplicado no puede ser un valor negativo")
    private Integer descuentoAplicado;

    @NotBlank(message = "El código identificador de la ropa es obligatorio")
    @Size(max = 8, message = "El código de la ropa no puede superar los 8 caracteres")
    private String ropaId;


}