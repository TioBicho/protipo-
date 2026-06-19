package com.e_commerce.ms_cliente.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-perfiles", path = "/api/perfiles")
public interface PerfilCliente {
    @GetMapping("/{id}")
    Object obtenerPerfilPorId(@PathVariable("id") Long id);
}