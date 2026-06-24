package com.e_commerce.ms_cliente.Controlador;

import com.e_commerce.ms_cliente.DTO.RegistroRequestDTO;
import com.e_commerce.ms_cliente.DTO.UsuarioDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloUsuario;
import com.e_commerce.ms_cliente.Servicio.ServicioUsuario;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class ControladorUsuario {

    @Autowired
    private ServicioUsuario service;


    // URL: POST http://localhost:8095/api/usuarios/cliente/12345678
    @PostMapping("/{rut}")
    public ResponseEntity<EntityModel<ModeloUsuario>> crearUsuarioParaCliente(
            @PathVariable Long rut,
            @RequestBody RegistroRequestDTO dto) {
        ModeloUsuario nuevo = service.guardarUsuario(rut, dto);
        EntityModel<ModeloUsuario> model = EntityModel.of(nuevo,
                linkTo(methodOn(ControladorUsuario.class).buscarPorId(nuevo.getId())).withSelfRel(),
                linkTo(methodOn(ControladorUsuario.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }
    //  http://localhost:8091/api/usuarios/todos
    @GetMapping("/todos")
    public ResponseEntity<List<ModeloUsuario>> listar() {
        log.info("Petición GET recibida para listar todas las cuentas de usuario");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // http://localhost:8091/api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ModeloUsuario> buscarPorId(@PathVariable("id") Long id) {
        log.info("Petición GET recibida para buscar usuario con ID: {}", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // http://localhost:8091/api/usuarios/filtrar/rol/{rol}
    @GetMapping("/filtrar/rol/{rol}")
    public ResponseEntity<List<ModeloUsuario>> filtrarPorRol(@PathVariable("rol") String rol) {
        log.info("Petición GET recibida para filtrar usuarios bajo el rol: {}", rol);
        return ResponseEntity.ok(service.listarPorRol(rol));
    }
    // http://localhost:8091/api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> eliminar(@PathVariable("id") Long id) {
        log.info("Petición DELETE recibida para remover el usuario ID: {}", id);
        service.eliminarUsuario(id);

        java.util.Map<String, String> respuesta = new java.util.HashMap<>();
        respuesta.put("mensaje", "El usuario ha sido eliminado correctamente del sistema.");
        return ResponseEntity.ok(respuesta);
    }
    //http://localhost:8091/api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloUsuario>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RegistroRequestDTO dto) {
        ModeloUsuario editado = service.actualizarUsuario(id, dto);
        EntityModel<ModeloUsuario> model = EntityModel.of(editado,
                linkTo(methodOn(ControladorUsuario.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(ControladorUsuario.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }
}