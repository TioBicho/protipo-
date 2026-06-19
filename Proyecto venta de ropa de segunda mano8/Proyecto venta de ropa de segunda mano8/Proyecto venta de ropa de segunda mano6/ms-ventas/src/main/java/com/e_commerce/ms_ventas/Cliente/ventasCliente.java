package com.e_commerce.ms_ventas.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cliente", contextId = "clienteVentas", path = "/api/usuarios")
public interface ventasCliente {
    @GetMapping("/{id}")
    String verificarUsuarioExiste(@PathVariable("id") Long id);
}