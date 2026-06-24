package com.e_commerce.ms_ventas.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-prendas", path = "/api/prendas")
public interface DetalleClient {

    @GetMapping("/{id}")
    String verificarPrendaExiste(@PathVariable("id") String id);
}