package com.e_commerce.ms_cliente.Controlador;

import com.e_commerce.ms_cliente.Cliente.UsuarioClient;
import com.e_commerce.ms_cliente.DTO.ClienteDTO;
import com.e_commerce.ms_cliente.DTO.RegistroRequestDTO;
import com.e_commerce.ms_cliente.DTO.TipoClienteDTO;
import com.e_commerce.ms_cliente.DTO.UsuarioDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloCliente;
import com.e_commerce.ms_cliente.Servicio.ServicioCliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ControladorCliente {

    @Autowired
    private ServicioCliente service;
    @Autowired
    private UsuarioClient usuarioClient;

    // http://localhost:8095/api/clientes/guardar
    @PostMapping("/guardar")
    public ResponseEntity<ModeloCliente> crear(@Valid @RequestBody RegistroRequestDTO request) {

        return new ResponseEntity<>(service.guardar(request), HttpStatus.CREATED);
    }
    //http://localhost:8095/api/clientes/todos
    @GetMapping("/todos")
    public ResponseEntity<List<ModeloCliente>> listar() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // http://localhost:8095/api/clientes/12345678
    @GetMapping("/{rut}")
    public ResponseEntity<EntityModel<ModeloCliente>> buscarPorRut(@PathVariable Long rut) {
        ModeloCliente cliente = service.obtenerPorRut(rut);

        EntityModel<ModeloCliente> modelo = EntityModel.of(cliente);

        modelo.add(linkTo(methodOn(ControladorCliente.class).buscarPorRut(rut)).withSelfRel());

        modelo.add(linkTo(methodOn(ControladorCliente.class).listar()).withRel("todos-los-clientes"));

        return ResponseEntity.ok(modelo);
    }

    // http://localhost:8095/api/clientes/buscar?keyword=juan
    @GetMapping("/buscar")
    public ResponseEntity<List<ModeloCliente>> buscarPorNombre(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(service.buscarClientesPorNombre(keyword));
    }

    // http://localhost:8095/api/clientes/12345678
    @PutMapping("/{rut}")
    public ResponseEntity<ModeloCliente> modificar(@PathVariable Long rut, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(service.actualizarCliente(rut, dto));
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