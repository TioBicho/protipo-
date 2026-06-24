package com.e_commerce.ms_ventas.Cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-cliente", contextId = "clientePuntos")
public interface ClientePuntosClient {
    @PutMapping("/api/clientes/agregar-puntos/{rut}")
    void agregarPuntos(@PathVariable Long rut, @RequestParam Integer puntos);
}