package com.e_commerce.ms_prendas.Controlador;

import com.e_commerce.ms_prendas.DTO.PrendaDTO;
import com.e_commerce.ms_prendas.Modelo.ModeloPrenda;
import com.e_commerce.ms_prendas.Servicio.PrendaService;
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
@RequestMapping("/api/prendas")
public class ControladorPrenda {

    @Autowired
    private PrendaService service;

    // http://localhost:8086/api/prendas/guardar
    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<ModeloPrenda>> crear(@Valid @RequestBody PrendaDTO dto) {
        log.info("Petición POST recibida para crear prenda con SKU: {}", dto.getId());
        ModeloPrenda nueva = service.guardarConDTO(dto);
        EntityModel<ModeloPrenda> model = EntityModel.of(nueva,
                linkTo(methodOn(ControladorPrenda.class).buscar(nueva.getId())).withSelfRel(),
                linkTo(methodOn(ControladorPrenda.class).listar()).withRel("todas")
        );
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    //  http://localhost:8086/api/prendas/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloPrenda>> listar() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    // http://localhost:8086/api/prendas/{id_con_sku}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloPrenda>> buscar(@PathVariable String id) {
        EntityModel<ModeloPrenda> model = EntityModel.of(service.obtenerPorId(id),
                linkTo(methodOn(ControladorPrenda.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorPrenda.class).listar()).withRel("todas")
        );
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloPrenda>> actualizar(@PathVariable String id, @Valid @RequestBody PrendaDTO dto) {
        log.info("Petición PUT: Actualizar ficha de prenda: {}", id);
        ModeloPrenda prenda = service.actualizarPrenda(id, dto);
        EntityModel<ModeloPrenda> model = EntityModel.of(prenda,
                linkTo(methodOn(ControladorPrenda.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ControladorPrenda.class).listar()).withRel("todas")
        );
        return ResponseEntity.ok(model);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable String id) {
        log.info("Petición DELETE: Retirar de catálogo la prenda: {}", id);
        service.eliminarPrenda(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "La prenda fue removida del catálogo físico con éxito.");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/tipo-ropa/{id}")
    public ResponseEntity<List<ModeloPrenda>> obtenerPorTipoRopa(@PathVariable Long id) {
        log.info("🎮 Controlador: Petición GET para listar prendas por tipo de ropa ID: {}", id);
        List<ModeloPrenda> prendas = service.listarPorTipoRopa(id);
        return ResponseEntity.ok(prendas);
    }


    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ModeloPrenda>> obtenerPorCategoria(@PathVariable Long id) {
        log.info("🎮 Controlador: Petición GET para listar prendas por categoría ID: {}", id);
        List<ModeloPrenda> prendas = service.listarPorCategoria(id);
        return ResponseEntity.ok(prendas);
    }
}