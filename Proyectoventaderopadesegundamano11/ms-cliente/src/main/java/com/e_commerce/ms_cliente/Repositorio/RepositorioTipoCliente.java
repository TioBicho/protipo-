package com.e_commerce.ms_cliente.Repositorio;

import com.e_commerce.ms_cliente.Modelo.ModeloTipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioTipoCliente extends JpaRepository<ModeloTipoCliente, Long> {

    List<ModeloTipoCliente> findByCategoriaCliente(String categoriaCliente);

    //  Filtrar categorías con puntajes altos
    @Query("SELECT t FROM ModeloTipoCliente t WHERE t.puntosMinimos >= :minPuntos")
    List<ModeloTipoCliente> buscarCategoriasPremium(@Param("minPuntos") Integer minPuntos);
}