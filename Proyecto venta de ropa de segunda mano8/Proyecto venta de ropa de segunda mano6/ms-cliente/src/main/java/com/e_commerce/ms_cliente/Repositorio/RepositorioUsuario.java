package com.e_commerce.ms_cliente.Repositorio;

import com.e_commerce.ms_cliente.Modelo.ModeloUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<ModeloUsuario, Long> {

    // Buscar por email
    Optional<ModeloUsuario> findByEmail(String email);

    // Buscar por ID de perfil
    Optional<ModeloUsuario> findByPerfilIdPerfil(Long perfilIdPerfil);

    // Filtro optimizado por tipo de rol ignorando mayúsculas
    @Query("SELECT u FROM ModeloUsuario u WHERE UPPER(u.rol) = UPPER(:rol)")
    List<ModeloUsuario> buscarUsuariosPorRol(@Param("rol") String rol);
}