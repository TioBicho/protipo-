package com.e_commerce.ms_cliente.Servicio;

import com.e_commerce.ms_cliente.DTO.RegistroRequestDTO;
import com.e_commerce.ms_cliente.DTO.UsuarioDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloCliente;
import com.e_commerce.ms_cliente.Modelo.ModeloUsuario;
import com.e_commerce.ms_cliente.Repositorio.RepositorioCliente;
import com.e_commerce.ms_cliente.Repositorio.RepositorioPerfil;
import com.e_commerce.ms_cliente.Repositorio.RepositorioTipoCliente;
import com.e_commerce.ms_cliente.Repositorio.RepositorioUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioClienteTest {

    // 1. Mockeamos TODAS las dependencias de tu ServicioCliente
    @Mock
    private RepositorioCliente repositorioCliente;

    @Mock
    private RepositorioTipoCliente repositorioTipoCliente;

    @Mock
    private RepositorioUsuario usuarioRepository;

    @Mock
    private RepositorioPerfil perfilRepository;

    // 2. Inyectamos los mocks en tu clase de servicio real
    @InjectMocks
    private ServicioCliente servicioCliente;

    // --- PRUEBA 1: Buscar un cliente que SÍ existe (Camino Feliz) ---
    @Test
    void testObtenerPorRut_Exito() {
        // Preparación (Arrange)
        Long rutBuscado = 19123456L;
        ModeloCliente clienteSimulado = new ModeloCliente();
        clienteSimulado.setRut(rutBuscado);
        clienteSimulado.setNombreCliente("Juan");

        // Le decimos a Mockito: "Cuando busquen por este RUT, devuelve este cliente"
        when(repositorioCliente.findById(rutBuscado)).thenReturn(Optional.of(clienteSimulado));

        // Ejecución (Act)
        ModeloCliente resultado = servicioCliente.obtenerPorRut(rutBuscado);

        // Verificación (Assert)
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombreCliente());
        assertEquals(rutBuscado, resultado.getRut());

        // Comprobamos que el repositorio fue llamado exactamente una vez
        verify(repositorioCliente, times(1)).findById(rutBuscado);
    }

    // --- PRUEBA 2: Buscar un cliente que NO existe (Manejo de Excepciones) ---
    @Test
    void testObtenerPorRut_NoEncontrado() {
        // Preparación
        Long rutBuscado = 99999999L;
        // Simulamos que la base de datos no encontró nada (Optional vacío)
        when(repositorioCliente.findById(rutBuscado)).thenReturn(Optional.empty());

        // Ejecución y Verificación
        // Comprobamos que nuestro servicio lanza la excepción esperada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCliente.obtenerPorRut(rutBuscado);
        });

        assertEquals("Cliente no encontrado con el RUT: " + rutBuscado, exception.getMessage());
        verify(repositorioCliente).findById(rutBuscado);
    }

    // --- PRUEBA 3: Validar regla de negocio en guardar() (Email duplicado) ---
    @Test
    void testGuardar_FallaPorEmailDuplicado() {
        // Preparación: Creamos un DTO falso simulando lo que enviaría el controlador
        RegistroRequestDTO requestDTO = new RegistroRequestDTO();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("test@correo.com");
        requestDTO.setUsuario(usuarioDTO);

        // Simulamos que el repositorio YA TIENE un usuario con ese correo
        when(usuarioRepository.findByEmail("test@correo.com")).thenReturn(Optional.of(new ModeloUsuario()));

        // Ejecución y Verificación
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCliente.guardar(requestDTO);
        });

        assertTrue(exception.getMessage().contains("ya está en uso"));

        // ¡MUY IMPORTANTE! Verificamos que al fallar el email, NUNCA se guardó el perfil ni el cliente
        verify(perfilRepository, never()).save(any());
        verify(repositorioCliente, never()).save(any());
    }
}