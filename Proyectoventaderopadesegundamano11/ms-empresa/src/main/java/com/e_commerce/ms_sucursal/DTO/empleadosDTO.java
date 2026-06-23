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
public class empleadosDTO {

    @NotNull(message = "El RUN es obligatorio.")
    private Integer run;

    @NotBlank(message = "El dígito verificador (DV) es obligatorio.")
    @Size(max = 1, message = "El DV debe tener exactamente 1 carácter.")
    private String dv;

    @NotBlank(message = "El nombre del empleado no puede estar vacío.")
    @Size(min = 2, max = 15, message = "El nombre debe tener entre 2 y 15 caracteres (límite de BD).")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio.")
    @Size(max = 8, message = "El apellido paterno no puede superar los 8 caracteres.")
    private String papellido;

    @NotBlank(message = "El apellido materno es obligatorio.")
    @Size(max = 8, message = "El apellido materno no puede superar los 8 caracteres.")
    private String mapellido;

    @NotBlank(message = "El cargo es obligatorio.")
    @Size(max = 15, message = "El cargo no puede superar los 15 caracteres.")
    private String cargo;

    @NotNull(message = "El ID de usuario asociado es obligatorio.")
    private Long usuarioId;

    @NotNull(message = "El ID de sucursal asignada es obligatorio.")
    private Long sucursalId;
}