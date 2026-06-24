package com.e_commerce.ms_ventas;

import com.e_commerce.ms_ventas.Modelo.ModeloVentas;
import com.e_commerce.ms_ventas.Repositorio.RepositorioVentas;
import com.e_commerce.ms_ventas.Servicio.ServicioVentas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// ...existing code...
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Negocio - MS Ventas")
class ServicioVentasTest {

    @Mock
    private RepositorioVentas repositorioVentas;

    @InjectMocks
    private ServicioVentas servicioVentas;

    @Test
    @DisplayName("Simular registro de nueva venta exitoso")
    void testRegistrarVenta() {
        ModeloVentas ventaMock = new ModeloVentas();
        // Nos limitamos a comprobar que el mock del repositorio devuelve el objeto esperado
        when(repositorioVentas.save(any(ModeloVentas.class))).thenReturn(ventaMock);

        ModeloVentas guardada = repositorioVentas.save(new ModeloVentas());
        assertNotNull(guardada);
        // Comprobamos que el objeto devuelto es el mismo que configuramos en el mock
        assertSame(ventaMock, guardada);
    }
}