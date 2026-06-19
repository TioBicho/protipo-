package com.e_commerce.ms_envios.Controlador;

import com.e_commerce.ms_envios.DTO.EnviosDTO;
import com.e_commerce.ms_envios.Modelo.ModeloEnvios;
import com.e_commerce.ms_envios.Servicio.ServicioEnvios;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/envios")
public class ControladorEnvios {

    @Autowired
    private ServicioEnvios service;

    // http://localhost:8084/api/envios/guardar
    @PostMapping("/guardar")
    public ResponseEntity<ModeloEnvios> crear(@Valid @RequestBody EnviosDTO dto) {
        log.info("Petición POST recibida para generar despacho de la venta: {}", dto.getVentasIdVenta());
        ModeloEnvios nuevoEnvio = service.guardarEnvio(dto);
        return new ResponseEntity<>(nuevoEnvio, HttpStatus.CREATED);
    }

    // http://localhost:8084/api/envios/todos
    @GetMapping("/todos")
    public ResponseEntity<List<ModeloEnvios>> listar() {
        log.info("Petición GET recibida para listar todos los despachos");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // http://localhost:8084/api/envios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloEnvios>> buscar(@PathVariable("id") Long id) {
        log.info("Petición GET recibida para buscar el envio ID: {}", id);

        ModeloEnvios envio = service.obtenerPorId(id);

        EntityModel<ModeloEnvios> modelo = EntityModel.of(envio);

        modelo.add(linkTo(methodOn(ControladorEnvios.class).buscar(id)).withSelfRel());

        modelo.add(linkTo(methodOn(ControladorEnvios.class).listar()).withRel("todos-los-envios"));

        return ResponseEntity.ok(modelo);
    }

    //http://localhost:8084/api/envios/filtrar/{estado}
    @GetMapping("/filtrar/{estado}")
    public ResponseEntity<List<ModeloEnvios>> buscarPorEstado(@PathVariable("estado") String estado) {
        log.info("Petición GET recibida para filtrar despachos por estado: {}", estado);
        return ResponseEntity.ok(service.listarPorEstado(estado));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ModeloEnvios> actualizar(@PathVariable Long id, @Valid @RequestBody EnviosDTO dto) {
        return ResponseEntity.ok(service.actualizarEnvio(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarEnvio(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Orden de despacho eliminada.");
        return ResponseEntity.ok(resp);
    }
}