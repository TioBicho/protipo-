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
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
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
    public ResponseEntity<EntityModel<ModeloEmpleados>> crear(@Valid @RequestBody ModeloEmpleados empleado) {
        log.info("Petición POST recibida para registrar empleado con RUN: {}", empleado.getRun());
        ModeloEmpleados nuevo = service.guardarEmpleado(empleado);
        EntityModel<ModeloEmpleados> model = EntityModel.of(nuevo,
                linkTo(methodOn(ControladorEmpleados.class).buscar(nuevo.getId())).withSelfRel(),
                linkTo(methodOn(ControladorEmpleados.class).listar()).withRel("todos")
        );
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }
    //http://localhost:8083/api/empleados/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloEmpleados>> listar() {
        log.info("Petición GET recibida para listar todos los empleados");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // http://localhost:8083/api/empleados/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloEmpleados>> buscar(@PathVariable Long id) {
        log.info("Petición GET recibida para buscar empleado con ID: {}", id);
        EntityModel<ModeloEmpleados> model = EntityModel.of(service.obtenerPorId(id),
                linkTo(methodOn(ControladorEmpleados.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorEmpleados.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }

    //http://localhost:8083/api/empleados/categoria/{cargo}
    @GetMapping("/categoria/{cargo}")
    public ResponseEntity<List<ModeloEmpleados>> buscarPorCategoria(@PathVariable("cargo") String cargo) {
        log.info("Petición GET recibida para filtrar empleados por cargo: {}", cargo);
        return ResponseEntity.ok(service.listarPorCategoria(cargo));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloEmpleados>> actualizar(@PathVariable Long id, @Valid @RequestBody empleadosDTO dto) {
        ModeloEmpleados emp = service.actualizarEmpleado(id, dto);
        EntityModel<ModeloEmpleados> model = EntityModel.of(emp,
                linkTo(methodOn(ControladorEmpleados.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorEmpleados.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarEmpleado(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Ficha de empleado eliminada.");
        return ResponseEntity.ok(resp);
    }
}