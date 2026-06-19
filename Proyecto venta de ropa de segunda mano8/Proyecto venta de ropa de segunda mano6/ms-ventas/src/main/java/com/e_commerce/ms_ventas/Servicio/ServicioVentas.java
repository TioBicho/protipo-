package com.e_commerce.ms_ventas.Servicio;

import com.e_commerce.ms_ventas.Cliente.ClientePuntosClient;
import com.e_commerce.ms_ventas.Cliente.EmpleadoVentaCliente;
import com.e_commerce.ms_ventas.Cliente.ventasCliente;
import com.e_commerce.ms_ventas.DTO.DetalleVentaDTO;
import com.e_commerce.ms_ventas.DTO.ventasDTO;
import com.e_commerce.ms_ventas.Modelo.ModeloDetalleVenta;
import com.e_commerce.ms_ventas.Modelo.ModeloVentas;
import com.e_commerce.ms_ventas.Repositorio.RepositorioVentas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServicioVentas {

    @Autowired
    private RepositorioVentas repository;

    @Autowired
    private ventasCliente clienteFeign;

    @Autowired
    private EmpleadoVentaCliente empleadoFeign;

    @Autowired
    private ClientePuntosClient clientePuntosClient;

    public ModeloVentas guardarConDTO(ventasDTO dto) {
        log.info("Iniciando validaciones para la venta de la boleta: {}", dto.getBoleta());

        if (repository.existsByCarrito(dto.getCarrito())) {
            throw new RuntimeException("No se puede registrar la venta: El carrito seleccionado ya tiene una boleta asociada.");
        }

        try {
            clienteFeign.verificarUsuarioExiste(Long.valueOf(dto.getClienteRut()));
        } catch (Exception e) {
            throw new RuntimeException("No se puede registrar la venta: El comprador ingresado no es válido o no existe en el sistema.");
        }

        try {
            empleadoFeign.verificarEmpleadoExiste(dto.getEmpleadoId());
        } catch (Exception e) {
            throw new RuntimeException("El ID de empleado ingresado no es válido o no trabaja en la empresa.");
        }

        ModeloVentas entidad = new ModeloVentas();
        entidad.setBoleta(dto.getBoleta());
        entidad.setCarrito(dto.getCarrito());
        entidad.setClienteRut(dto.getClienteRut());
        entidad.setEmpleadoId(dto.getEmpleadoId());

        if (dto.getDetalles() != null && !dto.getDetalles().isEmpty()) {
            int totalPuntos = 0;
            for (DetalleVentaDTO detalleDTO : dto.getDetalles()) {
                ModeloDetalleVenta detalle = new ModeloDetalleVenta();
                detalle.setPrecioOriginal(detalleDTO.getPrecioOriginal());
                detalle.setDescuentoAplicado(detalleDTO.getDescuentoAplicado());
                detalle.setRopaId(detalleDTO.getRopaId());
                entidad.getDetalles().add(detalle);

                int montoNeto = detalleDTO.getPrecioOriginal() - detalleDTO.getDescuentoAplicado();
                totalPuntos += montoNeto / 1000;
            }

            if (totalPuntos > 0) {
                try {
                    clientePuntosClient.agregarPuntos(Long.valueOf(dto.getClienteRut()), totalPuntos);
                    log.info("Puntos agregados al cliente RUT {}: {} puntos", dto.getClienteRut(), totalPuntos);
                } catch (Exception e) {
                    log.warn("No se pudieron agregar puntos al cliente: {}", e.getMessage());
                }
            }
        }

        return repository.save(entidad);
    }

    public List<ModeloVentas> obtenerTodas() {
        log.info("Buscando el historial completo de ventas en Oracle Cloud");
        return repository.findAll();
    }

    public ModeloVentas obtenerPorId(Long id) {
        log.info("Buscando la venta con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("La venta con ID " + id + " no existe en los registros."));
    }
    public ModeloVentas actualizarVenta(Long id, ventasDTO dto) {
        log.info("Modificando metadatos de la venta ID: {}", id);
        ModeloVentas venta = obtenerPorId(id);


        try {
            empleadoFeign.verificarEmpleadoExiste(dto.getEmpleadoId());
        } catch (Exception e) {
            throw new RuntimeException("No se puede actualizar la venta: El empleado informado no existe.");
        }


        venta.setBoleta(dto.getBoleta());
        venta.setCarrito(dto.getCarrito());
        venta.setClienteRut(dto.getClienteRut());
        venta.setEmpleadoId(dto.getEmpleadoId());

        return repository.save(venta);
    }
    public void eliminarVenta(Long id) {
        log.info("Anulando venta ID: {}", id);
        ModeloVentas venta = obtenerPorId(id);
        repository.delete(venta);
    }
}