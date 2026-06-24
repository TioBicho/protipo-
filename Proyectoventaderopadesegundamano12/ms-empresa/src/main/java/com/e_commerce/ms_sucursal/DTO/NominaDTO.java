package com.e_commerce.ms_sucursal.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NominaDTO {

    @NotNull(message = "El sueldo base es obligatorio.")
    @PositiveOrZero(message = "El sueldo base debe ser un valor mayor o igual a cero.")
    private Long sueldoBase;

    @NotNull(message = "El campo bonos es obligatorio (coloque 0 si no aplica).")
    @PositiveOrZero(message = "Los bonos no pueden ser negativos.")
    private Long bonos;

    @NotNull(message = "El campo descuentos es obligatorio (coloque 0 si no aplica).")
    @PositiveOrZero(message = "Los descuentos no pueden ser negativos.")
    private Long descuentos;

    @NotNull(message = "El ID del empleado asociado es obligatorio.")
    private Long empleadoId;
}