package com.e_commerce.ms_envios.Repositorio;

import com.e_commerce.ms_envios.Modelo.ModeloRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioRegion extends JpaRepository<ModeloRegion, Long> {

    //  Buscar por el nombre exacto de la región.
    Optional<ModeloRegion> findByNombreRegion(String nombreRegion);

    //Búsqueda personalizadacon coincidencia parcial inmune a mayúsculas/minúsculas y sirve para buscar sub-cadenas (Ej: buscar "val" y que traiga "Valparaíso").
    @Query("SELECT r FROM ModeloRegion r WHERE UPPER(r.nombreRegion) LIKE UPPER(CONCAT('%', :termino, '%'))")
    List<ModeloRegion> buscarPorNombreContiene(@Param("termino") String termino);
}