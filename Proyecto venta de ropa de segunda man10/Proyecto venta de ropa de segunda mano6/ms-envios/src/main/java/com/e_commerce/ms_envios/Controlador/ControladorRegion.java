package com.e_commerce.ms_envios.Controlador;

import com.e_commerce.ms_envios.DTO.regionDTO;
import com.e_commerce.ms_envios.Modelo.ModeloRegion;
import com.e_commerce.ms_envios.Servicio.ServicioRegion;
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
@RequestMapping("/api/regiones")
public class ControladorRegion {

    @Autowired
    private ServicioRegion service;

    //http://localhost:8087/api/regiones/guardar
    @PostMapping("/guardar")
    public ResponseEntity<ModeloRegion> crear(@Valid @RequestBody regionDTO dto) {
        log.info("Petición POST recibida para registrar región: {}", dto.getNombreRegion());
        ModeloRegion nuevaRegion = service.guardarRegion(dto);
        return new ResponseEntity<>(nuevaRegion, HttpStatus.CREATED);
    }

    //http://localhost:8087/api/regiones/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloRegion>> listar() {
        log.info("Petición GET recibida para listar todas las regiones");
        return ResponseEntity.ok(service.obtenerTodas());
    }

    //http://localhost:8087/api/regiones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ModeloRegion> buscar(@PathVariable("id") Long id) {
        log.info("Petición GET recibida para buscar región con ID: {}", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    //http://localhost:8087/api/regiones/filtrar/{termino}
    @GetMapping("/filtrar/{termino}")
    public ResponseEntity<List<ModeloRegion>> filtrarPorNombre(@PathVariable("termino") String termino) {
        log.info("Petición GET recibida para filtrar regiones que contengan: {}", termino);
        return ResponseEntity.ok(service.listarPorNombreParcial(termino));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ModeloRegion> actualizar(@PathVariable Long id, @Valid @RequestBody regionDTO dto) {
        return ResponseEntity.ok(service.actualizarRegion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarRegion(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Región eliminada correctamente.");
        return ResponseEntity.ok(resp);
    }
}