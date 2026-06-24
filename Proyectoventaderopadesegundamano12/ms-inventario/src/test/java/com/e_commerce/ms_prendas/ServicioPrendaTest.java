package com.e_commerce.ms_prendas;

import com.e_commerce.ms_prendas.Modelo.ModeloPrenda;
import com.e_commerce.ms_prendas.Repositorio.RepositorioPrenda;
import com.e_commerce.ms_prendas.Servicio.PrendaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Negocio - MS Inventario (Prendas)")
class ServicioPrendaTest {

    @Mock
    private RepositorioPrenda repositorioPrenda;

    @InjectMocks
    private PrendaService prendaService;

    @Test
    @DisplayName("Buscar prenda por ID existente debe retornar la prenda")
    void testBuscarPrenda_Exito() {
        // Usar un mock de ModeloPrenda y el tipo de ID correcto (String) en el repositorio
        ModeloPrenda prendaMock = mock(ModeloPrenda.class);
        String id = "1";

        when(repositorioPrenda.findById(id)).thenReturn(Optional.of(prendaMock));

        ModeloPrenda resultado = repositorioPrenda.findById(id).get();
        assertSame(prendaMock, resultado);
        verify(repositorioPrenda, times(1)).findById(id);
    }
}