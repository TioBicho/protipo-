package com.e_commerce.ms_cliente;

import com.e_commerce.ms_cliente.DTO.ClienteDTO;
import com.e_commerce.ms_cliente.DTO.PerfilDTO;
import com.e_commerce.ms_cliente.DTO.RegistroRequestDTO;
import com.e_commerce.ms_cliente.DTO.TipoClienteDTO;
import com.e_commerce.ms_cliente.DTO.UsuarioDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloCliente;
import com.e_commerce.ms_cliente.Modelo.ModeloPerfil;
import com.e_commerce.ms_cliente.Modelo.ModeloUsuario;
import com.e_commerce.ms_cliente.Repositorio.RepositorioCliente;
import com.e_commerce.ms_cliente.Repositorio.RepositorioPerfil;
import com.e_commerce.ms_cliente.Repositorio.RepositorioTipoCliente;
import com.e_commerce.ms_cliente.Repositorio.RepositorioUsuario;
import com.e_commerce.ms_cliente.Servicio.ServicioCliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioClienteTest {

    @Mock
    private RepositorioCliente repositorioCliente;

    @Mock
    private RepositorioTipoCliente repositorioTipoCliente;

    @Mock
    private RepositorioUsuario usuarioRepository;

    @Mock
    private RepositorioPerfil perfilRepository;

    @InjectMocks
    private ServicioCliente servicioCliente;

    // --- PRUEBA 1: Buscar un cliente que SÍ existe (Camino Feliz) ---
    @Test
    void testObtenerPorRut_Exito() {
        Long rutBuscado = 19123456L;
        ModeloCliente clienteSimulado = new ModeloCliente();
        clienteSimulado.setRut(rutBuscado);
        clienteSimulado.setNombreCliente("Juan");

        // CORREGIDO: findByID -> findById
        when(repositorioCliente.findById(rutBuscado)).thenReturn(Optional.of(clienteSimulado));

        ModeloCliente resultado = servicioCliente.obtenerPorRut(rutBuscado);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombreCliente());
        assertEquals(rutBuscado, resultado.getRut());

        // CORREGIDO: findByID -> findById
        verify(repositorioCliente, times(1)).findById(rutBuscado);
    }

    // --- PRUEBA 2: Buscar un cliente que NO existe (Manejo de Excepciones) ---
    @Test
    void testObtenerPorRut_NoEncontrado() {
        Long rutBuscado = 99999999L;
        // CORREGIDO: findByID -> findById
        when(repositorioCliente.findById(rutBuscado)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioCliente.obtenerPorRut(rutBuscado);
        });

        assertEquals("Cliente no encontrado con el RUT: " + rutBuscado, exception.getMessage());
        // CORREGIDO: findByID -> findById
        verify(repositorioCliente).findById(rutBuscado);
    }

    // --- PRUEBA 3: Validar regla de negocio en guardar() (Email duplicado) ---
    @Test
    void testGuardar_FallaPorEmailDuplicado() {
        RegistroRequestDTO requestDTO = new RegistroRequestDTO();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("test@correo.com");
        requestDTO.setUsuario(usuarioDTO);

        when(usuarioRepository.findByEmail("test@correo.com")).thenReturn(Optional.of(new ModeloUsuario()));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> servicioCliente.guardar(requestDTO));

        assertTrue(exception.getMessage().contains("ya está en uso"));

        verify(perfilRepository, never()).save(any());
        verify(repositorioCliente, never()).save(any());
    }

    // --- PRUEBA 4: Guardar un cliente exitosamente (Camino Feliz) ---
    @Test
    void testGuardar_Exito() {
        // 1. Preparación (Arrange)
        RegistroRequestDTO requestDTO = new RegistroRequestDTO();

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("nuevo@correo.com");
        usuarioDTO.setPassword("1234");
        requestDTO.setUsuario(usuarioDTO);

        PerfilDTO perfilDTO = new PerfilDTO();
        perfilDTO.setTelefono("+56912345678");
        requestDTO.setPerfil(perfilDTO);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setRut(11111111L);
        clienteDTO.setNombreCliente("Maria");
        TipoClienteDTO tipoClienteDTO = new TipoClienteDTO();
        tipoClienteDTO.setId(1L);
        clienteDTO.setTipoCliente(tipoClienteDTO);
        requestDTO.setCliente(clienteDTO);

        // Simulamos el comportamiento de las validaciones de base de datos
        when(usuarioRepository.findByEmail("nuevo@correo.com")).thenReturn(Optional.empty());
        when(repositorioTipoCliente.existsById(1L)).thenReturn(true);

        // Simulamos la acción de guardado en la base de datos
        when(perfilRepository.save(any(ModeloPerfil.class))).thenAnswer(i -> i.getArguments()[0]);
        when(usuarioRepository.save(any(ModeloUsuario.class))).thenAnswer(i -> i.getArguments()[0]);
        when(repositorioCliente.save(any(ModeloCliente.class))).thenAnswer(i -> i.getArguments()[0]);

        // 2. Ejecución (Act)
        ModeloCliente resultado = servicioCliente.guardar(requestDTO);

        // 3. Verificación (Assert)
        assertNotNull(resultado);
        assertEquals(11111111L, resultado.getRut());
        assertEquals("Maria", resultado.getNombreCliente());

        // Verificamos que se haya guardado cada entidad exactamente 1 vez
        verify(perfilRepository, times(1)).save(any(ModeloPerfil.class));
        verify(usuarioRepository, times(1)).save(any(ModeloUsuario.class));
        verify(repositorioCliente, times(1)).save(any(ModeloCliente.class));
    }
}