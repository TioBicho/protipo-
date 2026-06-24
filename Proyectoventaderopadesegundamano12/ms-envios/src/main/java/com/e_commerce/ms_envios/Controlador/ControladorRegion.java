package com.e_commerce.ms_envios.Controlador;

import com.e_commerce.ms_envios.DTO.regionDTO;
import com.e_commerce.ms_envios.Modelo.ModeloRegion;
import com.e_commerce.ms_envios.Servicio.ServicioRegion;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Importaciones estáticas para HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/regiones")
public class ControladorRegion {

    @Autowired
    private ServicioRegion service;

    // POST: Crear región
    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<ModeloRegion>> crear(@Valid @RequestBody regionDTO dto) {
        log.info("Petición POST recibida para registrar región: {}", dto.getNombreRegion());
        ModeloRegion nuevaRegion = service.guardarRegion(dto);
        return new ResponseEntity<>(ensamblarRecurso(nuevaRegion), HttpStatus.CREATED);
    }

    // GET: Listar todas
    @GetMapping("/todas")
    public ResponseEntity<CollectionModel<EntityModel<ModeloRegion>>> listar() {
        log.info("Petición GET recibida para listar todas las regiones");

        List<EntityModel<ModeloRegion>> regiones = service.obtenerTodas().stream()
                .map(this::ensamblarRecurso)
                .toList();

        CollectionModel<EntityModel<ModeloRegion>> modeloCompleto = CollectionModel.of(regiones,
                linkTo(methodOn(ControladorRegion.class).listar()).withSelfRel());

        return ResponseEntity.ok(modeloCompleto);
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloRegion>> buscar(@PathVariable("id") Long id) {
        log.info("Petición GET recibida para buscar región con ID: {}", id);
        ModeloRegion region = service.obtenerPorId(id);
        return ResponseEntity.ok(ensamblarRecurso(region));
    }

    // GET: Filtrar por término (nombre)
    @GetMapping("/filtrar/{termino}")
    public ResponseEntity<CollectionModel<EntityModel<ModeloRegion>>> filtrarPorNombre(@PathVariable("termino") String termino) {
        log.info("Petición GET recibida para filtrar regiones que contengan: {}", termino);

        List<EntityModel<ModeloRegion>> regiones = service.listarPorNombreParcial(termino).stream()
                .map(this::ensamblarRecurso)
                .toList();

        CollectionModel<EntityModel<ModeloRegion>> modeloCompleto = CollectionModel.of(regiones,
                linkTo(methodOn(ControladorRegion.class).filtrarPorNombre(termino)).withSelfRel());

        return ResponseEntity.ok(modeloCompleto);
    }

    // PUT: Actualizar región
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloRegion>> actualizar(@PathVariable Long id, @Valid @RequestBody regionDTO dto) {
        ModeloRegion regionActualizada = service.actualizarRegion(id, dto);
        return ResponseEntity.ok(ensamblarRecurso(regionActualizada));
    }

    // DELETE: Eliminar región (Se mantiene sin HATEOAS, devuelve 200 OK con el Map)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarRegion(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Región eliminada correctamente.");
        return ResponseEntity.ok(resp);
    }

    // ==========================================
    // MÉTODO AUXILIAR PARA ENSAMBLAR LOS LINKS
    // ==========================================
    private EntityModel<ModeloRegion> ensamblarRecurso(ModeloRegion region) {
        EntityModel<ModeloRegion> recurso = EntityModel.of(region);

        // Link "self": Apunta a esta región en específico
        recurso.add(linkTo(methodOn(ControladorRegion.class).buscar(region.getId())).withSelfRel());

        // Link "volver": Ayuda al Frontend a saber dónde está la lista completa de regiones
        recurso.add(linkTo(methodOn(ControladorRegion.class).listar()).withRel("todas_las_regiones"));

        return recurso;
    }
}