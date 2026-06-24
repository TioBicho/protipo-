package com.e_commerce.ms_prendas.Repositorio;

import com.e_commerce.ms_prendas.Modelo.ModeloCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioCategoria extends JpaRepository<ModeloCategoria, Long> {


    Optional<ModeloCategoria> findByNombre(String nombre);


    @Query("SELECT c FROM ModeloCategoria c ORDER BY c.nombre ASC")
    List<ModeloCategoria> findAllOrdenadoPorNombre();
}