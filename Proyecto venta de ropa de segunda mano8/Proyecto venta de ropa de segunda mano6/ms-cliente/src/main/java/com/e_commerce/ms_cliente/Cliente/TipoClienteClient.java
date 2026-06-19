package com.e_commerce.ms_cliente.Cliente;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;


@FeignClient(name = "ms-tipocliente", path = "/api/tipo-clientes")
public interface TipoClienteClient {

    @GetMapping("/{id}")
    Map<String, Object> obtenerPorId(@PathVariable("id") Long id);
}