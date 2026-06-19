package com.e_commerce.ms_ventas.Servicio;

import com.e_commerce.ms_ventas.Cliente.ClientePuntosClient;
import com.e_commerce.ms_ventas.Cliente.DetalleClient;
import com.e_commerce.ms_ventas.DTO.DetalleVentaDTO;
import com.e_commerce.ms_ventas.Modelo.ModeloDetalleVenta;
import com.e_commerce.ms_ventas.Modelo.ModeloVentas;
import com.e_commerce.ms_ventas.Repositorio.RepositorioDetalleVenta;
import com.e_commerce.ms_ventas.Repositorio.RepositorioVentas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServicioDetalleVenta {

    @Autowired
    private RepositorioDetalleVenta repository;

    @Autowired
    private DetalleClient prendaClient;

    @Autowired
    private RepositorioVentas ventaRepository;

    @Autowired
    private ClientePuntosClient clientePuntosClient;

    public List<ModeloDetalleVenta> obtenerTodos() {
        log.info("Consultando todos los detalles de venta registrados");
        return repository.findAll();
    }

    public ModeloDetalleVenta obtenerPorId(Long id) {
        log.info("Buscando detalle de venta con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("El detalle de venta con ID " + id + " no existe."));
    }

    public void eliminarDetalle(Long id) {
        log.info("Quitando línea de detalle ID: {}", id);
        ModeloDetalleVenta detalle = obtenerPorId(id);
        repository.delete(detalle);
    }
    public ModeloDetalleVenta agregarAlCarrito(Long ventaId, DetalleVentaDTO dto) {
        log.info("Agregando prenda {} al carrito de la venta {}", dto.getRopaId(), ventaId);

        try {
            prendaClient.verificarPrendaExiste(dto.getRopaId());
        } catch (Exception e) {
            throw new RuntimeException("No se puede agregar: El código de ropa (SKU) ingresado no existe.");
        }

        if (dto.getDescuentoAplicado() > dto.getPrecioOriginal()) {
            throw new RuntimeException("El descuento aplicado no puede ser mayor que el precio original.");
        }

        ModeloDetalleVenta detalle = new ModeloDetalleVenta();
        detalle.setPrecioOriginal(dto.getPrecioOriginal());
        detalle.setDescuentoAplicado(dto.getDescuentoAplicado());
        detalle.setRopaId(dto.getRopaId());


        int montoNeto = dto.getPrecioOriginal() - dto.getDescuentoAplicado();
        int puntosGanados = montoNeto / 1000;
        if (puntosGanados > 0) {
            ModeloVentas venta = ventaRepository.findById(ventaId)
                    .orElseThrow(() -> new RuntimeException("Venta no existe."));
            clientePuntosClient.agregarPuntos(Long.valueOf(venta.getClienteRut()), puntosGanados);
        }

        return repository.save(detalle);
    }

    public void eliminarDelCarrito(Long detalleId) {
        log.info("Eliminando prenda del carrito, detalle ID: {}", detalleId);
        ModeloDetalleVenta detalle = obtenerPorId(detalleId);
        repository.delete(detalle);
    }
    public ModeloDetalleVenta actualizarDetalle(Long id, DetalleVentaDTO dto) {
        log.info("Modificando detalle de venta ID: {}", id);

        ModeloDetalleVenta detalle = obtenerPorId(id);

        if (dto.getDescuentoAplicado() > dto.getPrecioOriginal()) {
            throw new RuntimeException("El descuento aplicado no puede ser mayor que el precio original.");
        }

        try {
            prendaClient.verificarPrendaExiste(dto.getRopaId());
        } catch (Exception e) {
            throw new RuntimeException("No se puede actualizar: El código de ropa (SKU) ingresado no existe.");
        }

        detalle.setPrecioOriginal(dto.getPrecioOriginal());
        detalle.setDescuentoAplicado(dto.getDescuentoAplicado());
        detalle.setRopaId(dto.getRopaId());

        return repository.save(detalle);
    }
}