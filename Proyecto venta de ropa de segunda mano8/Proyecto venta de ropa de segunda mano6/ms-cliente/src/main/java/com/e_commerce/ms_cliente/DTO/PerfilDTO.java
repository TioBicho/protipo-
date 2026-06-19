package com.e_commerce.ms_cliente.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {

    @NotBlank(message = "El teléfono de contacto es obligatorio.")
    @Size(max = 12, message = "El número telefónico no puede superar los 12 caracteres.") // 🛡️ Límite físico de tu DDL
    @Pattern(regexp = "^\\+(56\\d{9}|51\\d{9}|54\\d{9}|55\\d{9}|591\\d{8}|598\\d{8})$", message = "El teléfono debe ser un formato válido de Chile (+56), Perú (+51), Argentina (+54), Brasil (+55), Bolivia (+591) o Uruguay (+598).")
    private String telefono;
}