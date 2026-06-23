package com.e_commerce.ms_prendas.Repositorio;

import com.e_commerce.ms_prendas.Modelo.ModeloPrenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioPrenda extends JpaRepository<ModeloPrenda, String> {


    List<ModeloPrenda> findByTipoRopaId(Long tipoRopaId);


    // Buscar por Categoría usando
    @Query(value = "SELECT * FROM ropa WHERE tipo_ropa_id_tipo_ropa = :idCategoria", nativeQuery = true)
    List<ModeloPrenda> buscarPorCategoria(@Param("idCategoria") Long idCategoria);



}