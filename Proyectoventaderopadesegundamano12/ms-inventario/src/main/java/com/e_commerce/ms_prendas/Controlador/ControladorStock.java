package com.e_commerce.ms_prendas.Controlador;

import com.e_commerce.ms_prendas.DTO.StockDTO;
import com.e_commerce.ms_prendas.Modelo.ModeloStock;
import com.e_commerce.ms_prendas.Servicio.ServicioStock;
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
@RequestMapping("/api/stock")
public class ControladorStock {

    @Autowired
    private ServicioStock service;

    // http://localhost:8088/api/stock/guardar
    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<ModeloStock>> registrarInventario(@Valid @RequestBody StockDTO dto) {
        log.info("Petición POST recibida para actualizar existencias de prenda: {}", dto.getRopaIdRopa());
        ModeloStock stock = service.guardarOActualizarStock(dto);
        EntityModel<ModeloStock> model = EntityModel.of(stock,
                linkTo(methodOn(ControladorStock.class).listarInventario()).withRel("todos")
        );
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    // http://localhost:8088/api/stock/todos
    @GetMapping("/todos")
    public ResponseEntity<List<ModeloStock>> listarInventario() {
        log.info("Petición GET recibida para ver todo el stock");
        return ResponseEntity.ok(service.obtenerTodo());
    }

    // http://localhost:8088/api/stock/critico/{limite}
    @GetMapping("/critico/{limite}")
    public ResponseEntity<List<ModeloStock>> buscarExistenciasCriticas(@PathVariable("limite") Integer limite) {
        log.info("Petición GET recibida para auditar quiebres de inventario bajo el límite: {}", limite);
        return ResponseEntity.ok(service.listarUnidadesCriticas(limite));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloStock>> actualizar(@PathVariable Long id, @Valid @RequestBody StockDTO dto) {
        ModeloStock stock = service.actualizarStock(id, dto);
        EntityModel<ModeloStock> model = EntityModel.of(stock,
                linkTo(methodOn(ControladorStock.class).listarInventario()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarStock(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Registro de inventario eliminado.");
        return ResponseEntity.ok(resp);
    }
}