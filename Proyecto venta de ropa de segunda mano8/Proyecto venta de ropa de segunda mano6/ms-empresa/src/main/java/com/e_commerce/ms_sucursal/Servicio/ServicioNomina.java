package com.e_commerce.ms_sucursal.Servicio;

import com.e_commerce.ms_sucursal.DTO.empleadosDTO;
import com.e_commerce.ms_sucursal.Modelo.ModeloEmpleados;
import com.e_commerce.ms_sucursal.Repositorio.RepositorioEmpleado;
import com.e_commerce.ms_sucursal.DTO.NominaDTO;
import com.e_commerce.ms_sucursal.Modelo.ModeloNomina;
import com.e_commerce.ms_sucursal.Repositorio.RepositorioNomina;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ServicioNomina {

    @Autowired
    private RepositorioNomina repository;

    @Autowired
    private RepositorioEmpleado repositoryEmpleado;

    public ModeloNomina guardarNomina(com.e_commerce.ms_sucursal.DTO.@Valid NominaDTO dto) {
        log.info("Iniciando procesamiento de nómina para el empleado con ID: {}", dto.getEmpleadoId());


        log.info("Consultando empleado en base de datos local por el ID: {}", dto.getEmpleadoId());
        repositoryEmpleado.findById(dto.getEmpleadoId())
                .orElseThrow(() -> {
                    log.error("Validación fallida: El empleado con ID {} no existe.", dto.getEmpleadoId());
                    return new RuntimeException("No se puede emitir la nómina: El empleado informado (ID " + dto.getEmpleadoId() + ") no existe.");
                });

        log.info("Validación exitosa: El empleado existe de forma local.");




        Long liquido = dto.getSueldoBase() + dto.getBonos() - dto.getDescuentos();

        if (liquido < 0) {
            throw new RuntimeException("No se puede registrar la nómina: Los descuentos superan los ingresos.");
        }

        ModeloNomina entidad = new ModeloNomina();
        entidad.setSueldoBase(dto.getSueldoBase());
        entidad.setBonos(dto.getBonos());
        entidad.setDescuentos(dto.getDescuentos());
        entidad.setSueldoLiquido(liquido);
        entidad.setFechaEmision(LocalDate.now());
        entidad.setEmpleadoId(dto.getEmpleadoId());

        return repository.save(entidad);
    }

    public List<ModeloNomina> obtenerTodas() {
        log.info("Consultando el histórico completo de liquidaciones emitidas.");
        return repository.findAll();
    }

    public ModeloNomina obtenerPorId(Long id) {
        log.info("Buscando registro de nómina con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("La nómina con ID " + id + " no existe en el sistema."));
    }

    public List<ModeloNomina> listarPorEmpleado(Long empleadoId) {
        log.info("Buscando liquidaciones asociadas al empleado ID: {}", empleadoId);
        return repository.findByEmpleadoId(empleadoId);
    }


    public List<ModeloNomina> listarPorSueldoLiquidoMinimo(Double monto) {
        log.info("Filtrando nóminas con un sueldo líquido mayor o igual a: ${}", monto);
        return repository.buscarPorSueldoLiquidoMayorQue(monto);
    }
    public ModeloNomina actualizarNomina(Long id, NominaDTO dto) {
        log.info("Recalculando liquidación de sueldo ID: {}", id);


        ModeloNomina nomina = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro contable no encontrado."));

        Long base = dto.getSueldoBase() != null ? dto.getSueldoBase() : 0L;
        Long bonos = dto.getBonos() != null ? dto.getBonos() : 0L;
        Long descuentos = dto.getDescuentos() != null ? dto.getDescuentos() : 0L;


        Long nuevoLiquido = base + bonos - descuentos;

        if (nuevoLiquido < 0) {
            log.error("Error de consistencia: El nuevo sueldo líquido calculado es negativo: {}", nuevoLiquido);
            throw new RuntimeException("Error financiero: El saldo líquido resultante no puede ser negativo.");
        }


        nomina.setSueldoBase(base);
        nomina.setBonos(bonos);
        nomina.setDescuentos(descuentos);
        nomina.setSueldoLiquido(nuevoLiquido);
        nomina.setEmpleadoId(dto.getEmpleadoId());

        log.info("Liquidación ID {} recalculada con éxito (Nuevo Líquido: ${}). Guardando en la nube.", id, nuevoLiquido);
        return repository.save(nomina);
    }

    public void eliminarNomina(Long id) {
        log.info("Removiendo registro de nómina ID: {}", id);
        ModeloNomina nomina = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("La nómina informada no existe."));
        repository.delete(nomina);
    }
}