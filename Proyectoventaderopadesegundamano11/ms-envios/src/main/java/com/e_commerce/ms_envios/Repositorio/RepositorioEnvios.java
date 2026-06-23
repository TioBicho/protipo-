package com.e_commerce.ms_envios.Repositorio;

import com.e_commerce.ms_envios.Modelo.ModeloEnvios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioEnvios extends JpaRepository<ModeloEnvios, Long> {

    // Buscar por el ID de la venta
    Optional<ModeloEnvios> findByVentasIdVenta(Long ventasIdVenta);

    // para buscar sub-cadenas sin importar mayúsculas .
    @Query("SELECT e FROM ModeloEnvios e WHERE UPPER(e.estado) LIKE UPPER(CONCAT('%', :estado, '%'))")
    List<ModeloEnvios> buscarPorEstadoContiene(@Param("estado") String estado);
}