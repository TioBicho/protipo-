package com.e_commerce.ms_prendas.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    @NotNull(message = "La cantidad física es obligatoria.")
    @PositiveOrZero(message = "La cantidad en inventario no puede ser un valor negativo.")
    private Integer cantidad;

    @NotBlank(message = "El estado del inventario es obligatorio.")
    @Size(max = 50, message = "El estado del inventario no puede superar los 50 caracteres.")
    private String estadoInventario;

    @NotBlank(message = "El ID de la prenda (ropa) es obligatorio.")
    @Size(max = 8, message = "El ID de la prenda no puede superar los 8 caracteres.")
    private String ropaIdRopa;

    @NotNull(message = "El ID de la sucursal es obligatorio.")
    private Long sucursalIdSucursal;
}