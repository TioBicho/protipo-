package com.e_commerce.ms_sucursal.Servicio;

import com.e_commerce.ms_sucursal.Cliente.UsuarioCliente;
import com.e_commerce.ms_sucursal.DTO.empleadosDTO;
import com.e_commerce.ms_sucursal.Modelo.ModeloEmpleados;
import com.e_commerce.ms_sucursal.Repositorio.RepositorioSucursal;
import com.e_commerce.ms_sucursal.Modelo.ModeloSucursal;
import com.e_commerce.ms_sucursal.DTO.sucursalDTO;
import com.e_commerce.ms_sucursal.Repositorio.RepositorioEmpleado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ServicioEmpleado {

    @Autowired
    private RepositorioEmpleado repository;

    @Autowired
    private UsuarioCliente usuarioFeignClient;

    @Autowired
    private RepositorioSucursal sucursalFeignClient;

    private static final List<String> CATEGORIAS_CARGOS = Arrays.asList(
            "ADMINISTRADOR", "VENDEDOR", "CAJERO", "REPONEDOR", "LOGISTICA"
    );

    public ModeloEmpleados guardarEmpleado(ModeloEmpleados empleado) {
        log.info("Iniciando proceso de registro para el empleado RUT: {}", empleado.getRun());


        if (empleado.getCargo() != null) {
            empleado.setCargo(empleado.getCargo().toUpperCase().trim());
        }

        if (!CATEGORIAS_CARGOS.contains(empleado.getCargo())) {
            log.error("Validación fallida: El cargo '{}' no pertenece a ninguna categoría válida.", empleado.getCargo());
            throw new RuntimeException("No se puede registrar al empleado: El cargo debe pertenecer a una categoría válida: " + CATEGORIAS_CARGOS);
        }

        if (repository.findByRun(empleado.getRun()).isPresent()) {
            log.error("Validación fallida: El RUN {} ya se encuentra registrado.", empleado.getRun());
            throw new RuntimeException("No se puede registrar al empleado: El RUN ingresado ya existe en el sistema.");
        }

        log.info("Empleado validado con éxito. Guardando registro en Oracle Cloud.");
        return repository.save(empleado);
    }

    public List<ModeloEmpleados> obtenerTodos() {
        log.info("Consultando la lista completa de empleados");
        return repository.findAll();
    }

    public ModeloEmpleados obtenerPorId(Long id) {
        log.info("Buscando empleado con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("El empleado con ID " + id + " no existe."));
    }

    public List<ModeloEmpleados> listarPorCategoria(String cargo) {
        String cargoNormalizado = cargo.toUpperCase().trim();
        log.info("Filtrando personal por categoría de cargo: {}", cargoNormalizado);
        return repository.buscarPorCargo(cargoNormalizado);
    }
    public ModeloEmpleados actualizarEmpleado(Long id, empleadosDTO dto) {
        log.info("Actualizando ficha del empleado ID: {}", id);
        ModeloEmpleados emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado."));
        try {
            usuarioFeignClient.obtenerUsuarioPorId(dto.getUsuarioId());
            sucursalFeignClient.findById(dto.getSucursalId());
        } catch (Exception e) {
            throw new RuntimeException("Error por Red: Verifique que el Usuario y la Sucursal informados existan.");
        }

        emp.setNombre(dto.getNombre());
        emp.setApellidoPaterno(dto.getPapellido());
        emp.setApellidoMaterno(dto.getMapellido());
        emp.setCargo(dto.getCargo());
        emp.setSucursalId(dto.getSucursalId());
        return repository.save(emp);
    }

    public void eliminarEmpleado(Long id) {
        log.info("Dando de baja al empleado ID: {}", id);
        ModeloEmpleados emp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("El empleado no existe."));
        repository.delete(emp);
    }
}