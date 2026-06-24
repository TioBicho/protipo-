package com.e_commerce.ms_cliente.Controlador;

import com.e_commerce.ms_cliente.DTO.ClienteDTO;
import com.e_commerce.ms_cliente.DTO.RegistroRequestDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloCliente;
import com.e_commerce.ms_cliente.Servicio.ServicioCliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/clientes")
public class ControladorCliente {

    @Autowired
    private ServicioCliente service;

    // http://localhost:8095/api/clientes/guardar
    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<ModeloCliente>> crear(@Valid @RequestBody RegistroRequestDTO request) {
        ModeloCliente cliente = service.guardar(request);
        EntityModel<ModeloCliente> model = EntityModel.of(cliente,
                linkTo(methodOn(ControladorCliente.class).buscarPorRut(cliente.getRut())).withSelfRel(),
                linkTo(methodOn(ControladorCliente.class).listar()).withRel("todos")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }
    //http://localhost:8095/api/clientes/todos
    @GetMapping("/todos")
    public ResponseEntity<List<ModeloCliente>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    //  http://localhost:8095/api/clientes/12345678
    @GetMapping("/{rut}")
    public ResponseEntity<EntityModel<ModeloCliente>> buscarPorRut(@PathVariable Long rut) {
        EntityModel<ModeloCliente> model = EntityModel.of(service.obtenerPorRut(rut),
                linkTo(methodOn(ControladorCliente.class).buscarPorRut(rut)).withSelfRel(),
                linkTo(methodOn(ControladorCliente.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }

    // http://localhost:8095/api/clientes/buscar?keyword=juan
    @GetMapping("/buscar")
    public ResponseEntity<List<ModeloCliente>> buscarPorNombre(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(service.buscarClientesPorNombre(keyword));
    }

    // http://localhost:8095/api/clientes/12345678
    @PutMapping("/{rut}")
    public ResponseEntity<EntityModel<ModeloCliente>> modificar(@PathVariable Long rut, @Valid @RequestBody ClienteDTO dto) {
        ModeloCliente cliente = service.actualizarCliente(rut, dto);
        EntityModel<ModeloCliente> model = EntityModel.of(cliente,
                linkTo(methodOn(ControladorCliente.class).buscarPorRut(rut)).withSelfRel(),
                linkTo(methodOn(ControladorCliente.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }

    // http://localhost:8095/api/clientes/12345678
    @DeleteMapping("/{rut}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long rut) {
        service.eliminarCliente(rut);
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Ficha de cliente eliminada con éxito de la base de datos.");
        return ResponseEntity.ok(respuesta);
    }
    @PutMapping("/actualizar-categoria/{rut}")
    public ResponseEntity<ModeloCliente> actualizarCategoria(@PathVariable Long rut) {
        return ResponseEntity.ok(service.actualizarCategoria(rut));
    }
}