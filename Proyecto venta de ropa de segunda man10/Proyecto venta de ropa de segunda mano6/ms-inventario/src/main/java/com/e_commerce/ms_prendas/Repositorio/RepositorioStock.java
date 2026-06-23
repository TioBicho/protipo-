package com.e_commerce.ms_prendas.Repositorio;

import com.e_commerce.ms_prendas.Modelo.ModeloStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioStock extends JpaRepository<ModeloStock, Long> {

    //Buscar registro por combinación de prenda y sucursal
    Optional<ModeloStock> findByRopaIdRopaAndSucursalIdSucursal(String ropaIdRopa, Long sucursalIdSucursal);

    // Buscar registros con unidades críticas (Alerta de reposición)
    @Query("SELECT s FROM ModeloStock s WHERE s.cantidad <= :limite")
    List<ModeloStock> buscarStockCritico(@Param("limite") Integer limite);
}