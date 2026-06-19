package com.e_commerce.ms_cliente.Repositorio;


import com.e_commerce.ms_cliente.Modelo.ModeloPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioPerfil extends JpaRepository<ModeloPerfil, Long> {

    //Buscar con Coincidencia exacta de teléfono
    List<ModeloPerfil> findByTelefono(String telefono);

    //buscar perfiles por su prefijo telefónico
    @Query("SELECT p FROM ModeloPerfil p WHERE p.telefono LIKE CONCAT(:prefix, '%')")
    List<ModeloPerfil> buscarPorPrefijoTelefono(@Param("prefix") String prefix);
}