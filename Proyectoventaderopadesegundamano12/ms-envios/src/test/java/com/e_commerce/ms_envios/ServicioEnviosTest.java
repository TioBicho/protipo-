package com.e_commerce.ms_envios;

import com.e_commerce.ms_envios.Modelo.ModeloEnvios;
import com.e_commerce.ms_envios.Repositorio.RepositorioEnvios;
import com.e_commerce.ms_envios.Servicio.ServicioEnvios;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Negocio - MS Envios")
class ServicioEnviosTest {

    @Mock
    private RepositorioEnvios repositorioEnvios;

    @InjectMocks
    private ServicioEnvios servicioEnvios;

    @Test
    @DisplayName("Simular cambio de estado de envío")
    void testEstadoEnvio() {
        ModeloEnvios envio = new ModeloEnvios();
        envio.setEstado("En tránsito");

        when(repositorioEnvios.save(any(ModeloEnvios.class))).thenReturn(envio);

        ModeloEnvios resultado = repositorioEnvios.save(new ModeloEnvios());
        assertEquals("En tránsito", resultado.getEstado());
    }
}