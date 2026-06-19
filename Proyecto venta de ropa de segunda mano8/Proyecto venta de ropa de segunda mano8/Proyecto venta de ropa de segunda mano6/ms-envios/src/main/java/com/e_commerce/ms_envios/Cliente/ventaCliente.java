package com.e_commerce.ms_envios.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-ventas", path = "/api/ventas")
public interface ventaCliente {

    @GetMapping("/{id}")
    Object obtenerVentaPorId(@PathVariable("id") Long id);
}