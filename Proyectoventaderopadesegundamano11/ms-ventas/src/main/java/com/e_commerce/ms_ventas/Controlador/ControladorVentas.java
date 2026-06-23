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
    public ResponseEntity<ModeloVentas> crear(@Valid @RequestBody ventasDTO dto) {
        log.info("Petición POST recibida para registrar venta. Boleta N°: {}, Carrito ID: {}",
                dto.getBoleta(), dto.getCarrito());
        ModeloVentas nuevaVenta = service.guardarConDTO(dto);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }

    // http://localhost:8092/api/ventas/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloVentas>> listar() {
        log.info("Petición GET recibida para listar todas las ventas registradas");
        return ResponseEntity.ok(service.obtenerTodas());
    }

    // http://localhost:8092/api/ventas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ModeloVentas> buscar(@PathVariable("id") Long id) {
        log.info("Petición GET recibida para buscar la venta con ID: {}", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ModeloVentas> actualizar(@PathVariable Long id, @Valid @RequestBody ventasDTO dto) {
        log.info("Petición PUT: Modificando cabecera de venta ID: {}", id);
        return ResponseEntity.ok(service.actualizarVenta(id, dto));
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