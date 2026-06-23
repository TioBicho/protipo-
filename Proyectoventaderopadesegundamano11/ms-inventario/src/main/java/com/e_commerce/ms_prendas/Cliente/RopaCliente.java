package com.e_commerce.ms_prendas.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-prendas", path = "/api/prendas")
public interface RopaCliente {

    @GetMapping("/{id}")
    Object obtenerRopaPorId(@PathVariable("id") String id);
}