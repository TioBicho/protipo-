package com.e_commerce.ms_sucursal.Repositorio;

import com.e_commerce.ms_sucursal.Modelo.ModeloNomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioNomina extends JpaRepository<ModeloNomina, Long> {
    // Permite ver el historial de liquidaciones de un trabajador por su ID
    List<ModeloNomina> findByEmpleadoId(Long empleadoId);

    // Consulta parámetros nombrados para filtrar nóminas de rentas altas
    @Query("SELECT n FROM ModeloNomina n WHERE n.sueldoLiquido >= :monto")
    List<ModeloNomina> buscarPorSueldoLiquidoMayorQue(@Param("monto") Double monto);
}
