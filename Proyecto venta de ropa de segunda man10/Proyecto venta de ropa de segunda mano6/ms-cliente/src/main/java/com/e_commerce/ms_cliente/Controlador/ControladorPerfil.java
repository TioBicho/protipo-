package com.e_commerce.ms_cliente.Controlador;

import com.e_commerce.ms_cliente.DTO.PerfilDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloPerfil;
import com.e_commerce.ms_cliente.Servicio.ServicioPerfil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/perfiles")
public class ControladorPerfil {

    @Autowired
    private ServicioPerfil service;

    @PostMapping("/guardar")
    public ResponseEntity<ModeloPerfil> crear(@Valid @RequestBody PerfilDTO dto) {
        return new ResponseEntity<>(service.guardarPerfil(dto), HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ModeloPerfil>> listarTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloPerfil> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/buscar-prefijo")
    public ResponseEntity<List<ModeloPerfil>> buscarPorPrefijo(@RequestParam("prefix") String prefix) {
        return ResponseEntity.ok(service.buscarPorPrefijo(prefix));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloPerfil> actualizar(@PathVariable Long id, @Valid @RequestBody PerfilDTO dto) {
        return ResponseEntity.ok(service.actualizarPerfil(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarPerfil(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Perfil removido correctamente del sistema.");
        return ResponseEntity.ok(resp);
    }
}