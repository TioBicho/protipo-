package com.e_commerce.ms_prendas.Repositorio;

import com.e_commerce.ms_prendas.Modelo.ModeloTipoRopa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioTipoRopa extends JpaRepository<ModeloTipoRopa, Long> {
    List<ModeloTipoRopa> findByMarca(String marca);

    @Query("SELECT t FROM ModeloTipoRopa t WHERE t.color = :color AND t.talla = :talla")
    List<ModeloTipoRopa> buscarPorColorYTalla(@Param("color") String color, @Param("talla") String talla);
}