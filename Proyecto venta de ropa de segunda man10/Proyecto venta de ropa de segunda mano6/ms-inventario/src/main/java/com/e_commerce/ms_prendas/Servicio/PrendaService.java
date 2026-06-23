package com.e_commerce.ms_prendas.Servicio;

import com.e_commerce.ms_prendas.DTO.PrendaDTO;
import com.e_commerce.ms_prendas.DTO.tipoRopaDTO;
import com.e_commerce.ms_prendas.Repositorio.RepositorioTipoRopa;
import com.e_commerce.ms_prendas.Modelo.ModeloTipoRopa;
import com.e_commerce.ms_prendas.Modelo.ModeloPrenda;
import com.e_commerce.ms_prendas.Repositorio.RepositorioPrenda;
import com.e_commerce.ms_prendas.Cliente.ClienteRopa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class PrendaService {

    @Autowired
    private RepositorioPrenda repository;

    @Autowired
    private ClienteRopa RopaClient;

    @Autowired
    private RepositorioTipoRopa tipoRopaRepository;

    private static final List<String> CUIDADOS_PERMITIDOS = List.of(
            "Lavar a mano",
            "Lavar con agua fria",
            "No usar secadora",
            "Limpieza en seco",
            "Prenda delicada"
    );

    public ModeloPrenda guardarConDTO(PrendaDTO dto) {
        if (!CUIDADOS_PERMITIDOS.contains(dto.getCuidados())) {
            throw new RuntimeException("Error: El cuidado '" + dto.getCuidados() +
                    "' no es válido. Opciones permitidas: " + CUIDADOS_PERMITIDOS);
        }

        ModeloPrenda entidad = new ModeloPrenda();
        entidad.setId(dto.getId());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setCuidados(dto.getCuidados());
        ModeloTipoRopa tipoRopa = new ModeloTipoRopa();
        tipoRopa.setDiseno(dto.getTipoRopa().getDiseno());
        tipoRopa.setEstilo(dto.getTipoRopa().getEstilo());
        tipoRopa.setColor(dto.getTipoRopa().getColor());
        tipoRopa.setComposicion(dto.getTipoRopa().getComposicion());
        tipoRopa.setDetalles(dto.getTipoRopa().getDetalles());
        tipoRopa.setTipoPrenda(dto.getTipoRopa().getTipoPrenda());
        tipoRopa.setGenero(dto.getTipoRopa().getGenero());
        tipoRopa.setTalla(dto.getTipoRopa().getTalla());
        tipoRopa.setMarca(dto.getTipoRopa().getMarca());
        tipoRopa.setEstadoPrenda(dto.getTipoRopa().getEstadoPrenda());
        tipoRopa.setCategoriaId(dto.getTipoRopa().getCategoriaId());
        tipoRopa = tipoRopaRepository.save(tipoRopa);

        entidad.setTipoRopa(tipoRopa);
        return repository.save(entidad);
    }
    public List<ModeloPrenda> obtenerTodas() {
        return repository.findAll();
    }

    public ModeloPrenda obtenerPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("La prenda con SKU " + id + " no existe en el sistema."));
    }
    public List<ModeloPrenda> listarPorTipoRopa(Long tipoRopaId) {
        log.info("🔍 Servicio: Buscando prendas para el Tipo de Ropa ID: {}", tipoRopaId);
        return repository.findByTipoRopaId(tipoRopaId);
    }

    public List<ModeloPrenda> listarPorCategoria(Long idCategoria) {
        log.info("🔮 Servicio: Buscando prendas en la Categoría ID: {} usando SQL Nativo", idCategoria);
        return repository.buscarPorCategoria(idCategoria);
    }

    public ModeloPrenda actualizarPrenda(String id, PrendaDTO dto) {
        log.info("Iniciando actualización para la prenda ID: {}", id);
        ModeloPrenda prendaActual = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: La prenda con ID " + id + " no existe."));
        if (!CUIDADOS_PERMITIDOS.contains(dto.getCuidados())) {
            throw new RuntimeException("Error: El cuidado '" + dto.getCuidados() + "' no es válido. Opciones permitidas: " + CUIDADOS_PERMITIDOS);
        }
        try {
            RopaClient.verificarTipoRopaExiste(dto.getTipoRopa().getId());
        } catch (Exception e) {
            throw new RuntimeException("El Tipo de Ropa seleccionado no existe.");
        }
        prendaActual.setDescripcion(dto.getDescripcion());
        prendaActual.setCuidados(dto.getCuidados());
        ModeloTipoRopa tipoRopa = new ModeloTipoRopa();
        tipoRopa.setId(dto.getTipoRopa().getId());
        prendaActual.setTipoRopa(tipoRopa);

        log.info("Datos validados correctamente para la actualización. Guardando...");
        return repository.save(prendaActual);
    }
    public void eliminarPrenda(String id) {
        log.info("Solicitud para eliminar la prenda ID: {}", id);
        ModeloPrenda prenda = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: La prenda con ID " + id + " no existe."));

        repository.delete(prenda);
        log.info("Prenda ID {} eliminada con éxito.", id);
    }
}