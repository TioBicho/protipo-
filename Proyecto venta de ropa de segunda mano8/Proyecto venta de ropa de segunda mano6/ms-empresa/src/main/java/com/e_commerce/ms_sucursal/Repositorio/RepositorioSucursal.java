package com.e_commerce.ms_sucursal.Repositorio;

import com.e_commerce.ms_sucursal.Modelo.ModeloSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioSucursal extends JpaRepository<ModeloSucursal, Long> {

    // Busca por el nuevo atributo de nombre
    Optional<ModeloSucursal> findByNombreSucursal(String nombreSucursal);

    // Consulta  Filtra sucursales por ciudad
    @Query("SELECT s FROM ModeloSucursal s WHERE UPPER(s.ciudad) LIKE UPPER(CONCAT('%', :termino, '%'))")
    List<ModeloSucursal> buscarPorCiudadContiene(@Param("termino") String termino);
}