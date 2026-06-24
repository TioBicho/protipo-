package com.e_commerce.ms_sucursal.Servicio;

import com.e_commerce.ms_sucursal.Cliente.sucursalCliente;
import com.e_commerce.ms_sucursal.DTO.sucursalDTO;
import com.e_commerce.ms_sucursal.Modelo.ModeloSucursal;
import com.e_commerce.ms_sucursal.Repositorio.RepositorioSucursal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServicioSucursal {

    @Autowired
    private RepositorioSucursal repository;

    @Autowired
    private sucursalCliente regionFeignClient;

    public ModeloSucursal guardarSucursal(sucursalDTO dto) {
        log.info("Iniciando validación y registro de sucursal: {}", dto.getNombreSucursal());


        if (repository.findByNombreSucursal(dto.getNombreSucursal()).isPresent()) {
            log.error("Validación fallida: El nombre '{}' ya existe.", dto.getNombreSucursal());
            throw new RuntimeException("Ya existe una sucursal con ese nombre.");
        }


        try {
            log.info("Consultando por red a ms-regiones por el ID: {}", dto.getRegionId());
            regionFeignClient.obtenerRegionPorId(dto.getRegionId()); //
            log.info("Sincronización exitosa: La región existe.");
        } catch (Exception e) {
            log.error("Validación por red fallida: La región con ID {} no existe o ms-regiones está caído.", dto.getRegionId());
            throw new RuntimeException("No se puede crear la sucursal: La región informada (ID " + dto.getRegionId() + ") no existe.");
        }


        ModeloSucursal entidad = new ModeloSucursal();
        entidad.setNombreSucursal(dto.getNombreSucursal());
        entidad.setCiudad(dto.getCiudad());
        entidad.setRegionId(dto.getRegionId());

        return repository.save(entidad);
    }

    public ModeloSucursal actualizarSucursal(Long id, sucursalDTO dto) {
        log.info("Actualizando sucursal ID: {}", id);
        ModeloSucursal sucursal = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada."));
        try {
            regionFeignClient.obtenerRegionPorId(dto.getRegionId());
        } catch (Exception e) {
            throw new RuntimeException("La región informada (ID " + dto.getRegionId() + ") no existe.");
        }

        sucursal.setNombreSucursal(dto.getNombreSucursal());
        sucursal.setCiudad(dto.getCiudad());
        sucursal.setRegionId(dto.getRegionId());
        return repository.save(sucursal);
    }

    public void eliminarSucursal(Long id) {
        log.info("Eliminando sucursal ID: {}", id);
        ModeloSucursal sucursal = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("La sucursal no existe."));
        repository.delete(sucursal);
    }
    public List<ModeloSucursal> obtenerTodas() { return repository.findAll(); }

    public ModeloSucursal obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("La sucursal no existe."));
    }
    public List<ModeloSucursal> listarPorCiudad(String ciudad) { return repository.buscarPorCiudadContiene(ciudad); }
}