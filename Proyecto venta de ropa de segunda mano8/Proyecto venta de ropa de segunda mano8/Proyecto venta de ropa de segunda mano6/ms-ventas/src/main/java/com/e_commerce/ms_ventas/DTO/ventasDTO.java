package com.e_commerce.ms_ventas.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ventasDTO {

    @NotBlank(message = "El número de boleta es obligatorio")
    @Size(max = 30, message = "El número de boleta no puede superar los 30 caracteres")
    private String boleta;

    @NotNull(message = "El identificador del carrito es obligatorio")
    private Integer carrito;

    @NotNull(message = "El RUT del cliente es obligatorio")
    private Integer clienteRut;

    @NotNull(message = "El ID del empleado es obligatorio")
    private Long empleadoId;

    @NotNull(message ="No puede venir vacio")
    private List<DetalleVentaDTO> detalles;
}