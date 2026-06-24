package com.e_commerce.ms_ventas.Controlador;

import com.e_commerce.ms_ventas.DTO.DetalleVentaDTO;
import com.e_commerce.ms_ventas.Modelo.ModeloDetalleVenta;
import com.e_commerce.ms_ventas.Servicio.ServicioDetalleVenta;
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
@RequestMapping("/api/detalleventas")
public class ControladorDetalleVenta {

    @Autowired
    private ServicioDetalleVenta service;

    // http://localhost:8082/api/detalleventas/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloDetalleVenta>> listar() {
        log.info("Petición GET recibida para listar el historial de detalles de ventas");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // http://localhost:8082/api/detalleventas/{id}

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloDetalleVenta>> buscar(@PathVariable Long id) {
        log.info("Petición GET recibida para buscar detalle de venta con ID: {}", id);
        EntityModel<ModeloDetalleVenta> model = EntityModel.of(service.obtenerPorId(id),
                linkTo(methodOn(ControladorDetalleVenta.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorDetalleVenta.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloDetalleVenta>> actualizar(@PathVariable Long id, @Valid @RequestBody DetalleVentaDTO dto) {
        log.info("Petición PUT: Modificando montos de la linea de detalle ID: {}", id);
        ModeloDetalleVenta detalle = service.actualizarDetalle(id, dto);
        EntityModel<ModeloDetalleVenta> model = EntityModel.of(detalle,
                linkTo(methodOn(ControladorDetalleVenta.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorDetalleVenta.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }
    // http://localhost:8082/api/detalleventas/guardar
    @PostMapping("/carrito/{ventaId}")
    public ResponseEntity<EntityModel<ModeloDetalleVenta>> agregarAlCarrito(
            @PathVariable Long ventaId,
            @Valid @RequestBody DetalleVentaDTO dto) {
        ModeloDetalleVenta detalle = service.agregarAlCarrito(ventaId, dto);
        EntityModel<ModeloDetalleVenta> model = EntityModel.of(detalle,
                linkTo(methodOn(ControladorDetalleVenta.class).buscar(detalle.getId())).withSelfRel(),
                linkTo(methodOn(ControladorDetalleVenta.class).listar()).withRel("todos")
        );
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }
    @DeleteMapping("/carritoeliminar/{detalleId}")
    public ResponseEntity<Map<String, String>> eliminarDelCarrito(@PathVariable Long detalleId) {
        service.eliminarDelCarrito(detalleId);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Prenda eliminada del carrito exitosamente.");
        return ResponseEntity.ok(resp);
    }
}