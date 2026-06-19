package com.e_commerce.ms_prendas.Servicio;

import com.e_commerce.ms_prendas.DTO.StockDTO;
import com.e_commerce.ms_prendas.Modelo.ModeloStock;
import com.e_commerce.ms_prendas.Repositorio.RepositorioStock;
import com.e_commerce.ms_prendas.Cliente.RopaCliente;
import com.e_commerce.ms_prendas.Cliente.SucursalCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ServicioStock {

    @Autowired
    private RepositorioStock repository;

    @Autowired
    private RopaCliente ropaFeignClient;

    @Autowired
    private SucursalCliente sucursalFeignClient;

    public ModeloStock guardarOActualizarStock(StockDTO dto) {
        log.info("Procesando actualización de inventario. Prenda: {}, Sucursal: {}", dto.getRopaIdRopa(), dto.getSucursalIdSucursal());

        try {
            log.info("Consultando por red a ms-prendas por el ID de ropa: {}", dto.getRopaIdRopa());
            ropaFeignClient.obtenerRopaPorId(dto.getRopaIdRopa());
            log.info("Sincronización exitosa: La prenda existe.");
        } catch (Exception e) {
            log.error("Validación por red fallida: La prenda con ID {} no existe.", dto.getRopaIdRopa());
            throw new RuntimeException("No se puede registrar stock: La prenda informada (ID " + dto.getRopaIdRopa() + ") no existe.");
        }


        try {
            log.info("Consultando por red a ms-sucursal por el ID de sucursal: {}", dto.getSucursalIdSucursal());
            sucursalFeignClient.obtenerSucursalPorId(dto.getSucursalIdSucursal());
            log.info("Sincronización exitosa: La sucursal existe.");
        } catch (Exception e) {
            log.error("Validación por red fallida: La sucursal con ID {} no existe o está caída.", dto.getSucursalIdSucursal());
            throw new RuntimeException("No se puede registrar stock: La sucursal informada (ID " + dto.getSucursalIdSucursal() + ") no existe.");
        }


        Optional<ModeloStock> stockExistente = repository.findByRopaIdRopaAndSucursalIdSucursal(
                dto.getRopaIdRopa(), dto.getSucursalIdSucursal()
        );

        if (stockExistente.isPresent()) {
            ModeloStock stockActual = stockExistente.get();
            int nuevaCantidad = stockActual.getCantidad() + dto.getCantidad();
            log.info("La prenda ya existe en esta sede. Incrementando cantidad de {} a {}.", stockActual.getCantidad(), nuevaCantidad);

            stockActual.setCantidad(nuevaCantidad);
            stockActual.setEstadoInventario(dto.getEstadoInventario());
            return repository.save(stockActual);
        }


        log.info("Prenda nueva para la sucursal. Creando registro de stock base en Oracle Cloud.");
        ModeloStock nuevoStock = new ModeloStock();
        nuevoStock.setCantidad(dto.getCantidad());
        nuevoStock.setEstadoInventario(dto.getEstadoInventario());
        nuevoStock.setRopaIdRopa(dto.getRopaIdRopa());
        nuevoStock.setSucursalIdSucursal(dto.getSucursalIdSucursal());

        return repository.save(nuevoStock);
    }

    public List<ModeloStock> obtenerTodo() {
        log.info("Consultando el inventario global de la cadena.");
        return repository.findAll();
    }

    public List<ModeloStock> listarUnidadesCriticas(Integer limite) {
        log.info("Buscando registros con stock crítico menor o igual a: {} unidades.", limite);
        return repository.buscarStockCritico(limite);
    }
    public ModeloStock actualizarStock(Long id, StockDTO dto) {
        log.info("Modificando existencias físicas del stock ID: {}", id);
        ModeloStock stock = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de inventario no encontrado."));

        repository.findByRopaIdRopaAndSucursalIdSucursal(dto.getRopaIdRopa(), dto.getSucursalIdSucursal()).ifPresent(otro -> {
            if (!otro.getId().equals(id)) {
                throw new RuntimeException("Operación inválida: Ya existe otra fila que controla esa prenda en dicha sucursal.");
            }
        });
        stock.setCantidad(dto.getCantidad());
        stock.setEstadoInventario(dto.getEstadoInventario());
        stock.setRopaIdRopa(dto.getRopaIdRopa());
        stock.setSucursalIdSucursal(dto.getSucursalIdSucursal());
        return repository.save(stock);
    }

    public void eliminarStock(Long id) {
        log.info("Borrando stock ID: {}", id);
        ModeloStock stock = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("El registro de stock no existe."));
        repository.delete(stock);
    }
}