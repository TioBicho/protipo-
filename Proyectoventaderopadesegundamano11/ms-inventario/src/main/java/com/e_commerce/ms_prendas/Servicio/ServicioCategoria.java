package com.e_commerce.ms_prendas.Servicio;

import com.e_commerce.ms_prendas.DTO.categoriaDTO;
import com.e_commerce.ms_prendas.Modelo.ModeloCategoria;
import com.e_commerce.ms_prendas.Repositorio.RepositorioCategoria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioCategoria {

    @Autowired
    private RepositorioCategoria repositoryCategoria;

    public ModeloCategoria guardarCategoria(categoriaDTO dto) {
        log.info("Creando nueva categoría: {}", dto.getNombreCategoria());

        repositoryCategoria.findByNombre(dto.getNombreCategoria()).ifPresent(c -> {
            throw new RuntimeException("La categoría ya se encuentra registrada.");
        });

        ModeloCategoria categoria = new ModeloCategoria();
        categoria.setNombre(dto.getNombreCategoria());
        return repositoryCategoria.save(categoria);
    }

    public List<ModeloCategoria> obtenerTodas() {
        return repositoryCategoria.findAllOrdenadoPorNombre(); // 🌟 Que calce con el repositorio
    }
    public ModeloCategoria obtenerPorId(Long id) {
        return repositoryCategoria.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

    public ModeloCategoria actualizarCategoria(Long id, categoriaDTO dto) {
        log.info("Actualizando categoría ID: {}", id);
        ModeloCategoria categoria = obtenerPorId(id);

        repositoryCategoria.findByNombre(dto.getNombreCategoria()).ifPresent(otra -> {
            if (!otra.getId().equals(id)) throw new RuntimeException("Ese nombre de categoría ya existe.");
        });

        categoria.setNombre(dto.getNombreCategoria());
        return repositoryCategoria.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        log.info("Eliminando categoría ID: {}", id);
        ModeloCategoria categoria = obtenerPorId(id);
        repositoryCategoria.delete(categoria);
    }
}