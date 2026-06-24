package com.e_commerce.ms_cliente.Servicio;

import com.e_commerce.ms_cliente.DTO.TipoClienteDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloTipoCliente;
import com.e_commerce.ms_cliente.Repositorio.RepositorioTipoCliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioTipoCliente {


    private final RepositorioTipoCliente repositorioTipoCliente;
    
    public ModeloTipoCliente guardar(TipoClienteDTO dto) {
        log.info("Registrando nueva categoría de cliente: {}", dto.getCategoriaCliente());

        List<ModeloTipoCliente> existentes = repositorioTipoCliente.findByCategoriaCliente(dto.getCategoriaCliente());
        if (!existentes.isEmpty()) {
            throw new RuntimeException("La categoría '" + dto.getCategoriaCliente() + "' ya existe registrada.");
        }

        ModeloTipoCliente tipo = new ModeloTipoCliente();
        tipo.setCategoriaCliente(dto.getCategoriaCliente());
        tipo.setPuntosMinimos(dto.getPuntosMinimos()   );
        tipo.setFechaRegistro(LocalDate.now());

        return repositorioTipoCliente.save(tipo);
    }


    public List<ModeloTipoCliente> obtenerTodos() {
        log.info("Consultando el catálogo global de tipos de cliente.");
        return repositorioTipoCliente.findAll();
    }


    public ModeloTipoCliente obtenerPorId(Long id) {
        log.info("Buscando categoría ID: {}", id);
        return repositorioTipoCliente.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría de cliente no encontrada con ID: " + id));
    }


    public ModeloTipoCliente actualizar(Long id, TipoClienteDTO dto) {
        log.info("Actualizando categoría ID: {}", id);
        ModeloTipoCliente tipo = obtenerPorId(id);

        List<ModeloTipoCliente> existentes = repositorioTipoCliente.findByCategoriaCliente(dto.getCategoriaCliente());
        if (!existentes.isEmpty()) {
            ModeloTipoCliente otro = existentes.get(0);
            if (!otro.getId().equals(id)) {
                throw new RuntimeException("Ya existe otra categoría activa con el nombre: " + dto.getCategoriaCliente());
            }
        }

        tipo.setCategoriaCliente(dto.getCategoriaCliente());
        tipo.setPuntosMinimos(dto.getPuntosMinimos());

        return repositorioTipoCliente.save(tipo);
    }

    public void eliminar(Long id) {
        log.info("Eliminando categoría ID: {}", id);
        ModeloTipoCliente tipo = obtenerPorId(id);
        repositorioTipoCliente.delete(tipo);
    }
}