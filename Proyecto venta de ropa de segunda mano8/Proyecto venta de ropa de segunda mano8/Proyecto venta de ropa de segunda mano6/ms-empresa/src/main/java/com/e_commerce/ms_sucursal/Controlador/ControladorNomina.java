package com.e_commerce.ms_sucursal.Controlador;


import com.e_commerce.ms_sucursal.DTO.NominaDTO;
import com.e_commerce.ms_sucursal.Modelo.ModeloNomina;
import com.e_commerce.ms_sucursal.Servicio.ServicioNomina;
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
@RequestMapping("/api/nominas")
public class ControladorNomina {

    @Autowired
    private ServicioNomina service;

    //http://localhost:8085/api/nominas/guardar
    @PostMapping("/guardar")
    public ResponseEntity<ModeloNomina> crear(@Valid @RequestBody NominaDTO dto) {
        log.info("Petición POST recibida para emitir nómina al empleado ID: {}", dto.getEmpleadoId());
        ModeloNomina nuevaNomina = service.guardarNomina(dto);
        return new ResponseEntity<>(nuevaNomina, HttpStatus.CREATED);
    }

    // GET: http://localhost:8085/api/nominas/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloNomina>> listar() {
        log.info("Petición GET recibida para listar todo el histórico de nóminas");
        return ResponseEntity.ok(service.obtenerTodas());
    }

    // GET: http://localhost:8085/api/nominas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ModeloNomina> buscarPorId(@PathVariable("id") Long id) {
        log.info("Petición GET recibida para buscar liquidación con ID: {}", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // GET: http://localhost:8085/api/nominas/empleado/{empleadoId}
    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<ModeloNomina>> buscarPorEmpleado(@PathVariable("empleadoId") Long empleadoId) {
        log.info("Petición GET recibida para recuperar liquidaciones del empleado ID: {}", empleadoId);
        return ResponseEntity.ok(service.listarPorEmpleado(empleadoId));
    }

    // GET: http://localhost:8085/api/nominas/filtrar/sueldo/{monto}
    @GetMapping("/filtrar/sueldo/{monto}")
    public ResponseEntity<List<ModeloNomina>> filtrarPorSueldoLiquidoMinimo(@PathVariable("monto") Double monto) {
        log.info("Petición GET recibida para filtrar nóminas con sueldo líquido mayor o igual a: ${}", monto);
        return ResponseEntity.ok(service.listarPorSueldoLiquidoMinimo(monto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ModeloNomina> actualizar(@PathVariable Long id, @Valid @RequestBody NominaDTO dto) {
        return ResponseEntity.ok(service.actualizarNomina(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarNomina(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Registro de nómina eliminado exitosamente.");
        return ResponseEntity.ok(resp);
    }
}