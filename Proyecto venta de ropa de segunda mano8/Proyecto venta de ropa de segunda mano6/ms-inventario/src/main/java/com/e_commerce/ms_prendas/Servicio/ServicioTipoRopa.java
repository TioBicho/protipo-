package com.e_commerce.ms_prendas.Servicio;

import com.e_commerce.ms_prendas.DTO.tipoRopaDTO;
import com.e_commerce.ms_prendas.Modelo.ModeloTipoRopa;
import com.e_commerce.ms_prendas.Repositorio.RepositorioCategoria;
import com.e_commerce.ms_prendas.Repositorio.RepositorioTipoRopa;
import com.e_commerce.ms_prendas.Repositorio.RepositorioStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServicioTipoRopa {

    @Autowired
    private RepositorioTipoRopa repository;
    @Autowired
    private RepositorioStock repositoryStock;
    @Autowired
    private RepositorioCategoria repositorioCategoria;

    private void validarCategoriaLocal(Long categoriaId) {
        log.info("Consultando categoría en base de datos local por el ID: {}", categoriaId);
        repositorioCategoria.findById(categoriaId)
                .orElseThrow(() -> {
                    log.error("Validación fallida: La categoría con ID {} no existe.", categoriaId);
                    return new RuntimeException("Error de validación: La categoría informada (ID " + categoriaId + ") no existe.");
                });

        log.info("Validación exitosa: La categoría existe de forma local.");
    }
    public ModeloTipoRopa guardarTipoRopa(tipoRopaDTO dto) {
        log.info("Registrando nuevo tipo de prenda: {}", dto.getTipoPrenda());
        validarCategoriaLocal(dto.getCategoriaId());

        ModeloTipoRopa tipo = new ModeloTipoRopa();
        tipo.setDiseno(dto.getDiseno());
        tipo.setEstilo(dto.getEstilo());
        tipo.setColor(dto.getColor());
        tipo.setComposicion(dto.getComposicion());
        tipo.setDetalles(dto.getDetalles());
        tipo.setTipoPrenda(dto.getTipoPrenda());
        tipo.setGenero(dto.getGenero());
        tipo.setTalla(dto.getTalla());
        tipo.setMarca(dto.getMarca());
        tipo.setEstadoPrenda(dto.getEstadoPrenda());
        tipo.setCategoriaId(dto.getCategoriaId());

        return repository.save(tipo);
    }

    public List<ModeloTipoRopa> obtenerTodos() {
        return repository.findAll();
    }

    public ModeloTipoRopa obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de ropa no encontrado con ID: " + id));
    }

    public ModeloTipoRopa actualizarTipoRopa(Long id, tipoRopaDTO dto) {
        log.info("Actualizando tipo de ropa ID: {}", id);
        ModeloTipoRopa tipo = obtenerPorId(id);
        validarCategoriaLocal(dto.getCategoriaId());

        tipo.setDiseno(dto.getDiseno());
        tipo.setEstilo(dto.getEstilo());
        tipo.setColor(dto.getColor());
        tipo.setComposicion(dto.getComposicion());
        tipo.setDetalles(dto.getDetalles());
        tipo.setTipoPrenda(dto.getTipoPrenda());
        tipo.setGenero(dto.getGenero());
        tipo.setTalla(dto.getTalla());
        tipo.setMarca(dto.getMarca());
        tipo.setEstadoPrenda(dto.getEstadoPrenda());
        tipo.setCategoriaId(dto.getCategoriaId());

        return repository.save(tipo);
    }

    public void eliminarTipoRopa(Long id) {
        log.info("Borrando tipo de ropa ID: {}", id);
        ModeloTipoRopa tipo = obtenerPorId(id);
        repository.delete(tipo);
    }
    public List<ModeloTipoRopa> listarPorMarca(String marca) {
        log.info("Filtrando catálogo de prendas por la marca: {}", marca);
        return repository.findByMarca(marca);
    }

    public List<ModeloTipoRopa> listarPorColorYTalla(String color, String talla) {
        log.info("Filtrando catálogo por especificaciones -> Color: {}, Talla: {}", color, talla);
        return repository.buscarPorColorYTalla(color, talla);
    }
}