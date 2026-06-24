package com.e_commerce.ms_envios.Controlador;

import com.e_commerce.ms_envios.DTO.EnviosDTO;
import com.e_commerce.ms_envios.Modelo.ModeloEnvios;
import com.e_commerce.ms_envios.Servicio.ServicioEnvios;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Importaciones estáticas obligatorias para construir las URLs automáticamente
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/envios")
public class ControladorEnvios {

    @Autowired
    private ServicioEnvios service;

    // POST: Crear despacho
    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<ModeloEnvios>> crear(@Valid @RequestBody EnviosDTO dto) {
        log.info("Petición POST recibida para generar despacho de la venta: {}", dto.getVentasIdVenta());
        ModeloEnvios nuevoEnvio = service.guardarEnvio(dto);
        return new ResponseEntity<>(ensamblarRecurso(nuevoEnvio), HttpStatus.CREATED);
    }

    // GET: Listar todos
    @GetMapping("/todos")
    public ResponseEntity<CollectionModel<EntityModel<ModeloEnvios>>> listar() {
        log.info("Petición GET recibida para listar todos los despachos");

        List<EntityModel<ModeloEnvios>> envios = service.obtenerTodos().stream()
                .map(this::ensamblarRecurso)
                .toList();

        CollectionModel<EntityModel<ModeloEnvios>> modeloCompleto = CollectionModel.of(envios,
                linkTo(methodOn(ControladorEnvios.class).listar()).withSelfRel());

        return ResponseEntity.ok(modeloCompleto);
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloEnvios>> buscar(@PathVariable("id") Long id) {
        log.info("Petición GET recibida para buscar el envío ID: {}", id);
        ModeloEnvios envio = service.obtenerPorId(id);
        return ResponseEntity.ok(ensamblarRecurso(envio));
    }

    // GET: Filtrar por estado
    @GetMapping("/filtrar/{estado}")
    public ResponseEntity<CollectionModel<EntityModel<ModeloEnvios>>> buscarPorEstado(@PathVariable("estado") String estado) {
        log.info("Petición GET recibida para filtrar despachos por estado: {}", estado);

        List<EntityModel<ModeloEnvios>> envios = service.listarPorEstado(estado).stream()
                .map(this::ensamblarRecurso)
                .toList();

        CollectionModel<EntityModel<ModeloEnvios>> modeloCompleto = CollectionModel.of(envios,
                linkTo(methodOn(ControladorEnvios.class).buscarPorEstado(estado)).withSelfRel());

        return ResponseEntity.ok(modeloCompleto);
    }

    // PUT: Actualizar despacho
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloEnvios>> actualizar(@PathVariable Long id, @Valid @RequestBody EnviosDTO dto) {
        ModeloEnvios envioActualizado = service.actualizarEnvio(id, dto);
        return ResponseEntity.ok(ensamblarRecurso(envioActualizado));
    }

    // DELETE: Eliminar despacho (No lleva HATEOAS porque el recurso deja de existir)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarEnvio(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Orden de despacho eliminada.");
        return ResponseEntity.ok(resp);
    }

    private EntityModel<ModeloEnvios> ensamblarRecurso(ModeloEnvios envio) {
        EntityModel<ModeloEnvios> recurso = EntityModel.of(envio);

        // Link "self": Apunta al endpoint de buscar este mismo objeto
        recurso.add(linkTo(methodOn(ControladorEnvios.class).buscar(envio.getId())).withSelfRel());

        // Link cruzado: Conecta este microservicio con ms-ventas (Puerto 8092)
        recurso.add(Link.of("http://localhost:8092/api/ventas/" + envio.getVentasIdVenta()).withRel("detalle_venta_original"));

        return recurso;
    }
}