package com.e_commerce.ms_ventas.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-empresa", path = "/api/empleados")
public interface EmpleadoVentaCliente {

    @GetMapping("/{id}")
    String verificarEmpleadoExiste(@PathVariable("id") Long id);
}