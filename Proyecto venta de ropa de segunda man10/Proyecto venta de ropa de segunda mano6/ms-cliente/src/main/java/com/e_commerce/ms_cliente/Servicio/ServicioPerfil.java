package com.e_commerce.ms_cliente.Servicio;

import com.e_commerce.ms_cliente.DTO.PerfilDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloPerfil;
import com.e_commerce.ms_cliente.Repositorio.RepositorioPerfil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioPerfil {

    private final RepositorioPerfil repository;

    public ModeloPerfil guardarPerfil(PerfilDTO dto) {
        log.info("Procesando creación de nuevo perfil con teléfono: {}", dto.getTelefono());

        List<ModeloPerfil> existentes = repository.findByTelefono(dto.getTelefono());
        if (!existentes.isEmpty()) {
            throw new RuntimeException("El teléfono '" + dto.getTelefono() + "' ya se encuentra registrado.");
        }

        ModeloPerfil perfil = new ModeloPerfil();
        perfil.setTelefono(dto.getTelefono());

        return repository.save(perfil);
    }

    public List<ModeloPerfil> obtenerTodos() {
        log.info("Consultando listado global de perfiles físicos.");
        return repository.findAll();
    }

    public ModeloPerfil obtenerPorId(Long id) {
        log.info("Buscando perfil ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID: " + id));
    }

    public List<ModeloPerfil> buscarPorPrefijo(String prefix) {
        log.info("Filtrando perfiles por prefijo: {}", prefix);
        return repository.buscarPorPrefijoTelefono(prefix);
    }

    public ModeloPerfil actualizarPerfil(Long id, PerfilDTO dto) {
        log.info("Actualizando número de contacto para el perfil ID: {}", id);
        ModeloPerfil perfil = obtenerPorId(id);

        List<ModeloPerfil> existentes = repository.findByTelefono(dto.getTelefono());
        if (!existentes.isEmpty()) {
            ModeloPerfil otro = existentes.get(0);
            if (!otro.getIdPerfil().equals(id)) {
                throw new RuntimeException("El teléfono '" + dto.getTelefono() + "' ya está siendo usado por otro perfil.");
            }
        }

        perfil.setTelefono(dto.getTelefono());
        return repository.save(perfil);
    }
    public void eliminarPerfil(Long id) {
        log.info("Eliminando permanentemente el perfil ID: {}", id);
        ModeloPerfil perfil = obtenerPorId(id);
        repository.delete(perfil);
    }
}