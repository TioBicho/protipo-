package com.e_commerce.ms_prendas.Controlador;

import com.e_commerce.ms_prendas.DTO.categoriaDTO;
import com.e_commerce.ms_prendas.Modelo.ModeloCategoria;
import com.e_commerce.ms_prendas.Servicio.ServicioCategoria;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class ControladorCategoria {

    @Autowired
    private ServicioCategoria servicioCategoria;

    // URL final: POST http://localhost:8081/api/categorias/crear
    @PostMapping("/crear")
    public ResponseEntity<EntityModel<ModeloCategoria>> crearCategoria(@Valid @RequestBody categoriaDTO dto) {
        ModeloCategoria nueva = servicioCategoria.guardarCategoria(dto);
        EntityModel<ModeloCategoria> model = EntityModel.of(nueva,
                linkTo(methodOn(ControladorCategoria.class).obtenerTodas()).withRel("todas")
        );
        return ResponseEntity.ok(model);
    }

    // URL final: GET http://localhost:8081/api/categorias/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloCategoria>> obtenerTodas() {
        return ResponseEntity.ok(servicioCategoria.obtenerTodas());
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EntityModel<ModeloCategoria>> actualizarCategoria(@PathVariable Long id, @Valid @RequestBody categoriaDTO dto) {
        ModeloCategoria actualizada = servicioCategoria.actualizarCategoria(id, dto);
        EntityModel<ModeloCategoria> model = EntityModel.of(actualizada,
                linkTo(methodOn(ControladorCategoria.class).obtenerTodas()).withRel("todas")
        );
        return ResponseEntity.ok(model);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        servicioCategoria.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}