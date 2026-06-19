package com.e_commerce.ms_sucursal.Controlador;

import com.e_commerce.ms_sucursal.DTO.empleadosDTO;
import com.e_commerce.ms_sucursal.Modelo.ModeloEmpleados;
import com.e_commerce.ms_sucursal.Servicio.ServicioEmpleado;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/empleados")
public class ControladorEmpleados {

    @Autowired
    private ServicioEmpleado service;

    //http://localhost:8082/api/empleados/guardar
    @PostMapping("/guardar")
    public ResponseEntity<ModeloEmpleados> crear(@Valid @RequestBody ModeloEmpleados empleado) {
        log.info("Petición POST recibida para registrar empleado con RUN: {}", empleado.getRun());
        ModeloEmpleados nuevoEmpleado = service.guardarEmpleado(empleado);
        return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
    }

    //http://localhost:8083/api/empleados/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloEmpleados>> listar() {
        log.info("Petición GET recibida para listar todos los empleados");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // http://localhost:8083/api/empleados/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloEmpleados>> buscar(@PathVariable("id") Long id) {
        log.info("Petición GET recibida para buscar empleado con ID: {}", id);

        ModeloEmpleados empleado = service.obtenerPorId(id);

        EntityModel<ModeloEmpleados> modelo = EntityModel.of(empleado);

        modelo.add(linkTo(methodOn(ControladorEmpleados.class).buscar(id)).withSelfRel());

        modelo.add(linkTo(methodOn(ControladorEmpleados.class).listar()).withRel("todos-los-empleados"));

        return ResponseEntity.ok(modelo);
    }

    //http://localhost:8083/api/empleados/categoria/{cargo}
    @GetMapping("/categoria/{cargo}")
    public ResponseEntity<List<ModeloEmpleados>> buscarPorCategoria(@PathVariable("cargo") String cargo) {
        log.info("Petición GET recibida para filtrar empleados por cargo: {}", cargo);
        return ResponseEntity.ok(service.listarPorCategoria(cargo));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ModeloEmpleados> actualizar(@PathVariable Long id, @Valid @RequestBody empleadosDTO dto) {
        return ResponseEntity.ok(service.actualizarEmpleado(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarEmpleado(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Ficha de empleado eliminada.");
        return ResponseEntity.ok(resp);
    }
}