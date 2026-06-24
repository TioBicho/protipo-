package com.e_commerce.ms_prendas.Controlador;

import com.e_commerce.ms_prendas.DTO.tipoRopaDTO;
import com.e_commerce.ms_prendas.Modelo.ModeloTipoRopa;
import com.e_commerce.ms_prendas.Servicio.ServicioTipoRopa;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/tiporopa")
public class ControladorTIpoRopa {

    @Autowired
    private ServicioTipoRopa service;

    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<ModeloTipoRopa>> crear(@Valid @RequestBody tipoRopaDTO dto) {
        log.info("Petición POST: Registrar nueva especificación de catálogo.");
        ModeloTipoRopa nuevo = service.guardarTipoRopa(dto);
        EntityModel<ModeloTipoRopa> model = EntityModel.of(nuevo,
                linkTo(methodOn(ControladorTIpoRopa.class).buscarPorId(nuevo.getId())).withSelfRel(),
                linkTo(methodOn(ControladorTIpoRopa.class).listarTodos()).withRel("todos")
        );
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }


    @GetMapping("/todos")
    public ResponseEntity<List<ModeloTipoRopa>> listarTodos() {
        log.info("Petición GET: Listar todo el catálogo estructural.");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloTipoRopa>> buscarPorId(@PathVariable Long id) {
        log.info("Petición GET: Buscando especificación ID: {}", id);
        EntityModel<ModeloTipoRopa> model = EntityModel.of(service.obtenerPorId(id),
                linkTo(methodOn(ControladorTIpoRopa.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(ControladorTIpoRopa.class).listarTodos()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloTipoRopa>> actualizar(@PathVariable Long id, @Valid @RequestBody tipoRopaDTO dto) {
        log.info("Petición PUT: Modificar especificación ID: {}", id);
        ModeloTipoRopa tipo = service.actualizarTipoRopa(id, dto);
        EntityModel<ModeloTipoRopa> model = EntityModel.of(tipo,
                linkTo(methodOn(ControladorTIpoRopa.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(ControladorTIpoRopa.class).listarTodos()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        log.info("Petición DELETE: Remover especificación ID: {}", id);
        service.eliminarTipoRopa(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Especificación de ropa eliminada correctamente.");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/filtrar/marca/{marca}")
    public ResponseEntity<List<ModeloTipoRopa>> filtrarPorMarca(@PathVariable String marca) {
        log.info("Petición GET: Filtrar por marca '{}'", marca);
        return ResponseEntity.ok(service.listarPorMarca(marca));
    }

    @GetMapping("/filtrar/buscar-especifico")
    public ResponseEntity<List<ModeloTipoRopa>> filtrarPorColorYTalla(
            @RequestParam("color") String color,
            @RequestParam("talla") String talla) {
        log.info("Petición GET: Filtrar combinando Color: {} y Talla: {}", color, talla);
        return ResponseEntity.ok(service.listarPorColorYTalla(color, talla));
    }
}