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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/detalleventas")
public class ControladorDetalleVenta {

    @Autowired
    private ServicioDetalleVenta service;

    // http://localhost:8082/api/detalleventas/guardar


    // http://localhost:8082/api/detalleventas/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloDetalleVenta>> listar() {
        log.info("Petición GET recibida para listar el historial de detalles de ventas");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // http://localhost:8082/api/detalleventas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ModeloDetalleVenta> buscar(@PathVariable("id") Long id) {
        log.info("Petición GET recibida para buscar detalle de venta con ID: {}", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ModeloDetalleVenta> actualizar(@PathVariable Long id, @Valid @RequestBody DetalleVentaDTO dto) {
        log.info("Petición PUT: Modificando montos de la línea de detalle ID: {}", id);
        return ResponseEntity.ok(service.actualizarDetalle(id, dto));
    }
    // http://localhost:8082/api/detalleventas/guardar
    @PostMapping("/carrito/{ventaId}")
    public ResponseEntity<ModeloDetalleVenta> agregarAlCarrito(
            @PathVariable Long ventaId,
            @Valid @RequestBody DetalleVentaDTO dto) {
        return new ResponseEntity<>(service.agregarAlCarrito(ventaId, dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/carritoeliminar/{detalleId}")
    public ResponseEntity<Map<String, String>> eliminarDelCarrito(@PathVariable Long detalleId) {
        service.eliminarDelCarrito(detalleId);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Prenda eliminada del carrito exitosamente.");
        return ResponseEntity.ok(resp);
    }
}