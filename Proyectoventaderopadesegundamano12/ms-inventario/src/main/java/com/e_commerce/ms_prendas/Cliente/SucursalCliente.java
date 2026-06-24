package com.e_commerce.ms_prendas.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-sucursal", url = "/api/sucursales")
public interface SucursalCliente {

    @GetMapping("/{id}")
    Object obtenerSucursalPorId(@PathVariable("id") Long id);
}