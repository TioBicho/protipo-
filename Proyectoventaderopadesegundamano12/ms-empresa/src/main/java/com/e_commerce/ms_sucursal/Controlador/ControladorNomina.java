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
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
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
    public ResponseEntity<EntityModel<ModeloNomina>> crear(@Valid @RequestBody NominaDTO dto) {
        log.info("Petición POST recibida para emitir nómina al empleado ID: {}", dto.getEmpleadoId());
        ModeloNomina nueva = service.guardarNomina(dto);
        EntityModel<ModeloNomina> model = EntityModel.of(nueva,
                linkTo(methodOn(ControladorNomina.class).buscarPorId(nueva.getId())).withSelfRel(),
                linkTo(methodOn(ControladorNomina.class).listar()).withRel("todos")
        );
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }


    // GET: http://localhost:8085/api/nominas/todas
    @GetMapping("/todas")
    public ResponseEntity<List<ModeloNomina>> listar() {
        log.info("Petición GET recibida para listar todo el histórico de nóminas");
        return ResponseEntity.ok(service.obtenerTodas());
    }

    // GET: http://localhost:8085/api/nominas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ModeloNomina>> buscarPorId(@PathVariable Long id) {
        log.info("Petición GET recibida para buscar liquidación con ID: {}", id);
        EntityModel<ModeloNomina> model = EntityModel.of(service.obtenerPorId(id),
                linkTo(methodOn(ControladorNomina.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(ControladorNomina.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
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
    public ResponseEntity<EntityModel<ModeloNomina>> actualizar(@PathVariable Long id, @Valid @RequestBody NominaDTO dto) {
        ModeloNomina nomina = service.actualizarNomina(id, dto);
        EntityModel<ModeloNomina> model = EntityModel.of(nomina,
                linkTo(methodOn(ControladorNomina.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(ControladorNomina.class).listar()).withRel("todos")
        );
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        service.eliminarNomina(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("mensaje", "Registro de nómina eliminado exitosamente.");
        return ResponseEntity.ok(resp);
    }
}