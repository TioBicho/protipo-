package com.e_commerce.ms_cliente.Repositorio;

import com.e_commerce.ms_cliente.Modelo.ModeloCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioCliente extends JpaRepository<ModeloCliente, Long> {

    // Buscar clientes por su apellido paterno exacto
    List<ModeloCliente> findByPapellido(String papellido);

    //Buscador por coincidencia parcial de nombre
    @Query("SELECT c FROM ModeloCliente c WHERE UPPER(c.nombreCliente) LIKE UPPER(CONCAT('%', :keyword, '%'))")
    List<ModeloCliente> buscarPorNombreClave(@Param("keyword") String keyword);

    Long rut(Long rut);
}