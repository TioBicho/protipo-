package com.e_commerce.ms_prendas.Controlador;

import com.e_commerce.ms_prendas.DTO.categoriaDTO;
import com.e_commerce.ms_prendas.Modelo.ModeloCategoria;
import com.e_commerce.ms_prendas.Servicio.ServicioCategoria;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class ControladorCategoria {

    @Autowired
    private ServicioCategoria servicioCategoria;

    // URL final: POST http://localhost:8081/api/categorias/crear
    @PostMapping("/crear")
    public ResponseEntity<ModeloCategoria> crearCategoria(@Valid @RequestBody categoriaDTO dto) {
        ModeloCategoria nuevaCategoria = servicioCategoria.guardarCategoria(dto);
        return ResponseEntity.ok(nuevaCategoria);
    }

    // URL final: GET http://localhost:8081/api/categorias/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloCategoria>> obtenerTodas() {
        return ResponseEntity.ok(servicioCategoria.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloCategoria>> obtenerPorId(@PathVariable Long id) {
        ModeloCategoria categoria = servicioCategoria.obtenerPorId(id);

        EntityModel<ModeloCategoria> modelo = EntityModel.of(categoria);
        modelo.add(linkTo(methodOn(ControladorCategoria.class).obtenerPorId(id)).withSelfRel());
        modelo.add(linkTo(methodOn(ControladorCategoria.class).obtenerTodas()).withRel("todas-las-categorias"));

        return ResponseEntity.ok(modelo);
    }
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ModeloCategoria> actualizarCategoria(@PathVariable Long id, @Valid @RequestBody categoriaDTO dto) {
        ModeloCategoria categoriaActualizada = servicioCategoria.actualizarCategoria(id, dto);
        return ResponseEntity.ok(categoriaActualizada);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        servicioCategoria.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}