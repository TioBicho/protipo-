package com.e_commerce.ms_ventas.Controlador;

import com.e_commerce.ms_ventas.DTO.ventasDTO;
import com.e_commerce.ms_ventas.Modelo.ModeloVentas;
import com.e_commerce.ms_ventas.Servicio.ServicioVentas;
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
@RequestMapping("/api/ventas")
public class ControladorVentas {

    @Autowired
    private ServicioVentas service;

    //http://localhost:8092/api/ventas/guardar
    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<ModeloVentas>> crear(@Valid @RequestBody ventasDTO dto) {
        log.info("Petición POST recibida para registrar venta. Boleta N°: {}, Carrito ID: {}", dto.getBoleta(), dto.getCarrito());
        ModeloVentas nuevaVenta = service.guardarConDTO(dto);
        EntityModel<ModeloVentas> model = EntityModel.of(nuevaVenta,
                linkTo(methodOn(ControladorVentas.class).buscar(nuevaVenta.getId())).withSelfRel(),
                linkTo(methodOn(ControladorVentas.class).listar()).withRel("todos")
        );
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    // http://localhost:8092/api/ventas/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloVentas>> listar() {
        log.info("Petición GET recibida para listar todas las ventas registradas");
        return ResponseEntity.ok(service.obtenerTodas());
    }

    // http://localhost:8092/api/ventas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloVentas>> buscar(@PathVariable Long id) {
        log.info("Petición GET recibida para buscar la venta con ID: {}", id);
        EntityModel<ModeloVentas> model = EntityModel.of(service.obtenerPorId(id),
                linkTo(methodOn(ControladorVentas.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorVentas.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloVentas>> actualizar(@PathVariable Long id, @Valid @RequestBody ventasDTO dto) {
        log.info("Petición PUT: Modificando cabecera de venta ID: {}", id);
        ModeloVentas venta = service.actualizarVenta(id, dto);
        EntityModel<ModeloVentas> model = EntityModel.of(venta,
                linkTo(methodOn(ControladorVentas.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorVentas.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        log.info("Petición DELETE: Anulando registro contable de venta ID: {}", id);
        service.eliminarVenta(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "La venta ha sido anulada y eliminada del sistema.");
        return ResponseEntity.ok(resp);
    }
}