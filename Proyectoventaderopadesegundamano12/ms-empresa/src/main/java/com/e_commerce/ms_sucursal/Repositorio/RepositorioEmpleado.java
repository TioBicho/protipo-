package com.e_commerce.ms_sucursal.Repositorio;

import com.e_commerce.ms_sucursal.Modelo.ModeloEmpleados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioEmpleado extends JpaRepository<ModeloEmpleados, Long> {

    // Buscar un empleado por su RUT
    Optional<ModeloEmpleados> findByRun(Integer run);

    // Consulta optimizada en JPQL para filtrar rápidamente empleados según su cargo o rol laboral
    @Query("SELECT e FROM ModeloEmpleados e WHERE e.cargo = :cargo")
    List<ModeloEmpleados> buscarPorCargo(@Param("cargo") String cargo);
}