package com.e_commerce.ms_sucursal.Controlador;

import com.e_commerce.ms_sucursal.DTO.sucursalDTO;
import com.e_commerce.ms_sucursal.Modelo.ModeloSucursal;
import com.e_commerce.ms_sucursal.Servicio.ServicioSucursal;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/sucursales")
public class ControladorSucursal {

    @Autowired
    private ServicioSucursal service;

    //http://localhost:8089/api/sucursales/guardar
    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<ModeloSucursal>> crear(@Valid @RequestBody sucursalDTO dto) {
        log.info("Petición POST recibida para crear sucursal: {}", dto.getNombreSucursal());
        ModeloSucursal nueva = service.guardarSucursal(dto);
        EntityModel<ModeloSucursal> model = EntityModel.of(nueva,
                linkTo(methodOn(ControladorSucursal.class).buscar(nueva.getId())).withSelfRel(),
                linkTo(methodOn(ControladorSucursal.class).listar()).withRel("todas")
        );
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    //http://localhost:8089/api/sucursales/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloSucursal>> listar() {
        log.info("Petición GET recibida para listar todas las sucursales");
        return ResponseEntity.ok(service.obtenerTodas());
    }

    //http://localhost:8089/api/sucursales/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloSucursal>> buscar(@PathVariable Long id) {
        log.info("Petición GET recibida para buscar la sucursal ID: {}", id);
        EntityModel<ModeloSucursal> model = EntityModel.of(service.obtenerPorId(id),
                linkTo(methodOn(ControladorSucursal.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorSucursal.class).listar()).withRel("todas")
        );
        return ResponseEntity.ok(model);
    }

    //  http://localhost:8089/api/sucursales/filtrar/{ciudad}
    @GetMapping("/filtrar/{ciudad}")
    public ResponseEntity<List<ModeloSucursal>> buscarPorCiudad(@PathVariable("ciudad") String ciudad) {
        log.info("Petición GET recibida para filtrar sucursales por la ciudad: {}", ciudad);
        return ResponseEntity.ok(service.listarPorCiudad(ciudad));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloSucursal>> actualizar(@PathVariable Long id, @Valid @RequestBody sucursalDTO dto) {
        ModeloSucursal sucursal = service.actualizarSucursal(id, dto);
        EntityModel<ModeloSucursal> model = EntityModel.of(sucursal,
                linkTo(methodOn(ControladorSucursal.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorSucursal.class).listar()).withRel("todas")
        );
        return ResponseEntity.ok(model);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarSucursal(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Sucursal removida con éxito.");
        return ResponseEntity.ok(resp);
    }
}