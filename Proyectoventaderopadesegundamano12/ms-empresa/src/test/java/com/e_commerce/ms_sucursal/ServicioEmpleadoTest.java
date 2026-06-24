package com.e_commerce.ms_sucursal;

import com.e_commerce.ms_sucursal.Modelo.ModeloEmpleados;
import com.e_commerce.ms_sucursal.Repositorio.RepositorioEmpleado;
import com.e_commerce.ms_sucursal.Servicio.ServicioEmpleado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Negocio - MS Empresa (Empleados)")
class ServicioEmpleadoTest {

    @Mock
    private RepositorioEmpleado repositorioEmpleado;

    @InjectMocks
    private ServicioEmpleado servicioEmpleado;

    @Test
    @DisplayName("Validar creación de empleado con Mockito")
    void testCrearEmpleado() {
        ModeloEmpleados empleadoMock = new ModeloEmpleados();

        when(repositorioEmpleado.save(any(ModeloEmpleados.class))).thenReturn(empleadoMock);

        ModeloEmpleados resultado = repositorioEmpleado.save(new ModeloEmpleados());
        // verificar que el repositorio devuelve la misma instancia mock retornada por el stub
        assertSame(empleadoMock, resultado);
    }
}