package com.e_commerce.ms_ventas.Repositorio;

import com.e_commerce.ms_ventas.Modelo.ModeloVentas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioVentas extends JpaRepository<ModeloVentas, Long> {

    // Busca todas las ventas asociadas al RUT de un cliente
    List<ModeloVentas> findByClienteRut(Integer clienteRut);

    // Consulta personalizada para traer una venta buscando por el número de boleta exacto
    @Query("SELECT v FROM ModeloVentas v WHERE v.boleta = :numeroBoleta")
    Optional<ModeloVentas> buscarPorNumeroBoleta(@Param("numeroBoleta") String numeroBoleta);
    boolean existsByCarrito(Integer carrito);
}