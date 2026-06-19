package com.e_commerce.ms_ventas.Repositorio;

import com.e_commerce.ms_ventas.Modelo.ModeloDetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioDetalleVenta extends JpaRepository<ModeloDetalleVenta, Long> {

    List<ModeloDetalleVenta> findByVenta_Id(Long id);

    // Consulta personalizada para buscar qué ventas incluyeron un SKU de prenda específico
    @Query("SELECT d FROM ModeloDetalleVenta d WHERE d.ropaId = :ropaId")
    List<ModeloDetalleVenta> buscarPorSkuPrenda(@Param("ropaId") String ropaId);
}