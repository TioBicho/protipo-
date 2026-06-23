package com.e_commerce.ms_envios.Servicio;

import com.e_commerce.ms_envios.DTO.EnviosDTO;
import com.e_commerce.ms_envios.Modelo.ModeloEnvios;
import com.e_commerce.ms_envios.Repositorio.RepositorioEnvios;
import com.e_commerce.ms_envios.Cliente.ventaCliente;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ServicioEnvios {

    @Autowired
    private RepositorioEnvios repository;

    @Autowired
    private ventaCliente ventaClient;

    public ModeloEnvios guardarEnvio(EnviosDTO dto) {
        log.info("Iniciando validación y registro de envío para la venta ID: {}", dto.getVentasIdVenta());

        if (repository.findByVentasIdVenta(dto.getVentasIdVenta()).isPresent()) {
            log.error("Validación fallida: La venta ID {} ya cuenta con un envío registrado.", dto.getVentasIdVenta());
            throw new RuntimeException("No se puede despachar: Esta venta ya tiene un envío asociado.");
        }

        try {
            log.info("Consultando al ms-ventas vía Feign por el ID: {}", dto.getVentasIdVenta());
            ventaClient.obtenerVentaPorId(dto.getVentasIdVenta());
        } catch (Exception e) {
            log.error("Error de red o ID no encontrado en ms-ventas para el ID: {}", dto.getVentasIdVenta());
            throw new RuntimeException("Error de despacho: La venta con ID " + dto.getVentasIdVenta() + " no existe en el sistema global.");
        }

        ModeloEnvios entidad = new ModeloEnvios();
        entidad.setDireccionDestino(dto.getDireccionDestino());
        entidad.setTransportista(dto.getTransportista());
        entidad.setNumeroSeguimiento(dto.getNumeroSeguimiento());
        entidad.setEstado(dto.getEstado());
        entidad.setFechaCreacion(LocalDate.now());

        return repository.save(entidad);
    }

    public List<ModeloEnvios> obtenerTodos() {
        log.info("Consultando el listado completo de despachos.");
        return repository.findAll();
    }

    public ModeloEnvios obtenerPorId(Long id) {
        log.info("Buscando envío con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("El envío con ID " + id + " no existe en el registro."));
    }
    public ModeloEnvios actualizarEnvio(Long id, EnviosDTO dto) {
        log.info("Iniciando actualización para el envío ID: {}", id);

        ModeloEnvios envio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún envío registrado con el ID: " + id));

        try {
            log.info("Verificando existencia de la venta ID {} vía Feign para la actualización...", dto.getVentasIdVenta());
            ventaClient.obtenerVentaPorId(dto.getVentasIdVenta());
        } catch (Exception e) {
            log.error("Validación fallida: La venta ID {} no existe en ms-ventas.", dto.getVentasIdVenta());
            throw new RuntimeException("Error de actualización: La venta con ID " + dto.getVentasIdVenta() + " no es válida.");
        }

        envio.setDireccionDestino(dto.getDireccionDestino());
        envio.setTransportista(dto.getTransportista());
        envio.setNumeroSeguimiento(dto.getNumeroSeguimiento());
        envio.setEstado(dto.getEstado());

        log.info("¡Envío ID {} actualizado exitosamente en Oracle Cloud!", id);
        return repository.save(envio);
    }

    public void eliminarEnvio(Long id) {
        log.info("Preparando la eliminación del envío ID: {}", id);

        ModeloEnvios envio = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: El envío con ID " + id + " no existe en el sistema."));


        repository.delete(envio);
        log.info("¡Envío ID {} eliminado correctamente del catálogo!", id);
    }

    public List<ModeloEnvios> listarPorEstado(String estado) {
        log.info("Filtrando despachos bajo el estado: {}", estado);
        return repository.buscarPorEstadoContiene(estado);
    }
}