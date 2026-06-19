package com.e_commerce.ms_cliente.Controlador;

import com.e_commerce.ms_cliente.DTO.TipoClienteDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloTipoCliente;
import com.e_commerce.ms_cliente.Servicio.ServicioTipoCliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/tipo_clientes")
public class ControladorTipoCliente {

    @Autowired
    private ServicioTipoCliente service;

    @PostMapping("/guardar")
    public ResponseEntity<ModeloTipoCliente> crear(@Valid @RequestBody TipoClienteDTO dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ModeloTipoCliente>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloTipoCliente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloTipoCliente> modificar(@PathVariable Long id, @Valid @RequestBody TipoClienteDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Categoría removida correctamente.");
        return ResponseEntity.ok(resp);
    }
    @PostMapping("/guardarcategoria")
    public ResponseEntity<ModeloTipoCliente> guardar(@RequestBody TipoClienteDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }
}