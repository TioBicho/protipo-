package com.e_commerce.ms_envios.Servicio;

import com.e_commerce.ms_envios.DTO.regionDTO;
import com.e_commerce.ms_envios.Modelo.ModeloRegion;
import com.e_commerce.ms_envios.Repositorio.RepositorioRegion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServicioRegion {

    @Autowired
    private RepositorioRegion repository;

    public ModeloRegion guardarRegion(regionDTO dto) {
        log.info("Iniciando validación para registrar la región: {}", dto.getNombreRegion());


        if (repository.findByNombreRegion(dto.getNombreRegion()).isPresent()) {
            log.error("Validación fallida: La región '{}' ya se encuentra registrada.", dto.getNombreRegion());
            throw new RuntimeException("No se puede registrar la región: Ya existe un registro con ese nombre.");
        }


        ModeloRegion entidad = new ModeloRegion();
        entidad.setNombreRegion(dto.getNombreRegion());

        log.info("Región validada con éxito. Guardando en Oracle Cloud.");
        return repository.save(entidad);
    }
    public ModeloRegion actualizarRegion(Long id, regionDTO dto) {
        log.info("Iniciando actualización para la región ID: {}", id);
        ModeloRegion region = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con ID: " + id));

        // Validar que el nuevo nombre no lo tenga otra región
        repository.findByNombreRegion(dto.getNombreRegion()).ifPresent(otra -> {
            if (!otra.getId().equals(id)) {
                throw new RuntimeException("Ya existe otra región registrada con el nombre: " + dto.getNombreRegion());
            }
        });

        region.setNombreRegion(dto.getNombreRegion());
        return repository.save(region);
    }

    public void eliminarRegion(Long id) {
        log.info("Eliminando región ID: {}", id);
        ModeloRegion region = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: Región no existe."));
        repository.delete(region);
    }

    public List<ModeloRegion> obtenerTodas() {
        log.info("Consultando el listado completo de regiones.");
        return repository.findAll();
    }

    public ModeloRegion obtenerPorId(Long id) {
        log.info("Buscando región con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("La región con ID " + id + " no existe en el sistema."));
    }


    public List<ModeloRegion> listarPorNombreParcial(String termino) {
        log.info("Filtrando regiones por coincidencia parcial: {}", termino);
        return repository.buscarPorNombreContiene(termino);
    }
}