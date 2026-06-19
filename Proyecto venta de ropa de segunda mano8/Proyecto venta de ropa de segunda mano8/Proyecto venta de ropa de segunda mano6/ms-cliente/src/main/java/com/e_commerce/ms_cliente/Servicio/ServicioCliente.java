package com.e_commerce.ms_cliente.Servicio;

import com.e_commerce.ms_cliente.DTO.ClienteDTO;
import com.e_commerce.ms_cliente.DTO.RegistroRequestDTO;
import com.e_commerce.ms_cliente.DTO.UsuarioDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloCliente;
import com.e_commerce.ms_cliente.Modelo.ModeloPerfil;
import com.e_commerce.ms_cliente.Modelo.ModeloTipoCliente;
import com.e_commerce.ms_cliente.Modelo.ModeloUsuario;
import com.e_commerce.ms_cliente.Repositorio.RepositorioCliente;
import com.e_commerce.ms_cliente.Repositorio.RepositorioPerfil;
import com.e_commerce.ms_cliente.Repositorio.RepositorioTipoCliente;

import com.e_commerce.ms_cliente.Repositorio.RepositorioUsuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioCliente {
    private final RepositorioCliente repositorioCliente;
    private final RepositorioTipoCliente repositorioTipoCliente; // Este te faltaba
    private final RepositorioUsuario usuarioRepository;
    private final RepositorioPerfil perfilRepository;
    @Transactional
    public ModeloCliente guardar(RegistroRequestDTO registroRequestDTO) {


        if (usuarioRepository.findByEmail(registroRequestDTO.getUsuario().getEmail()).isPresent()) {
            throw new RuntimeException("Error: El correo " + registroRequestDTO.getUsuario().getEmail() + " ya está en uso.");
        }

        ModeloPerfil perfil = new ModeloPerfil();
        perfil.setTelefono(registroRequestDTO.getPerfil().getTelefono());
        perfil = perfilRepository.save(perfil);

        ModeloUsuario usuario = new ModeloUsuario();
        usuario.setEmail(registroRequestDTO.getUsuario().getEmail());
        usuario.setPassword(registroRequestDTO.getUsuario().getPassword());
        usuario.setRol("CLIENTE");
        usuario.setPerfil(perfil);
        usuario = usuarioRepository.save(usuario);


        ModeloCliente cliente = new ModeloCliente();
        cliente.setRut(registroRequestDTO.getCliente().getRut());
        cliente.setDv(registroRequestDTO.getCliente().getDv());
        cliente.setNombreCliente(registroRequestDTO.getCliente().getNombreCliente());
        cliente.setFechaNacimiento(registroRequestDTO.getCliente().getFechaNacimiento());
        cliente.setPapellido(registroRequestDTO.getCliente().getPapellido());
        cliente.setMapellido(registroRequestDTO.getCliente().getMapellido());
        cliente.setPuntos(0);


        if (!repositorioTipoCliente.existsById(registroRequestDTO.getCliente().getTipoCliente().getId())) {
            throw new RuntimeException("Error: El tipo de cliente no existe en el sistema.");
        }

        ModeloTipoCliente tipoCliente = new ModeloTipoCliente();
        tipoCliente.setId(registroRequestDTO.getCliente().getTipoCliente().getId());
        cliente.setTipoCliente(tipoCliente);

        cliente.getUsuarios().add(usuario);

        return repositorioCliente.save(cliente);
    }


    public List<ModeloCliente> obtenerTodos() {
        log.info("Recuperando listado global de clientes.");
        return repositorioCliente.findAll();
    }


    public ModeloCliente obtenerPorRut(Long rut) {
        log.info("Buscando cliente con RUT: {}", rut);
        return repositorioCliente.findById(rut)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con el RUT: " + rut));
    }



    public ModeloCliente actualizarCliente(Long id, ClienteDTO dto) {
        log.info("Actualizando datos del cliente con ID: {}", id);

        ModeloCliente cliente = repositorioCliente.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el cliente con ID: " + id));


        cliente.setRut(dto.getRut());
        cliente.setDv(dto.getDv());
        cliente.setNombreCliente(dto.getNombreCliente());
        cliente.setPapellido(dto.getPapellido());
        cliente.setMapellido(dto.getMapellido());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());



        return repositorioCliente.save(cliente);
    }

    public void eliminarCliente(Long id) {
        log.info("Intentando eliminar cliente con ID: {}", id);

        ModeloCliente cliente = repositorioCliente.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: El cliente con ID " + id + " no existe en el sistema."));

        repositorioCliente.delete(cliente);
        log.info("¡Cliente con ID {} eliminado exitosamente!", id);
    }


    public List<ModeloCliente> buscarClientesPorNombre(String keyword) {
        log.info("Filtrando clientes por coincidencia de nombre: '{}'", keyword);
        return repositorioCliente.buscarPorNombreClave(keyword);
    }


    public ModeloCliente actualizarCategoria(Long rut) {
        ModeloCliente cliente = repositorioCliente.findById(rut)
                .orElseThrow(() -> new RuntimeException("No se encontró el cliente con ID: " + rut));

        int puntos = cliente.getTipoCliente().getPuntosMinimos();

        ModeloTipoCliente nuevaCategoria = repositorioTipoCliente
                .findAll()
                .stream()
                .filter(t -> puntos >= t.getPuntosMinimos())
                .max(Comparator.comparingInt(ModeloTipoCliente::getPuntosMinimos))
                .orElseThrow(() -> new RuntimeException("No se encontró categoria."));

        cliente.setTipoCliente(nuevaCategoria);
        return repositorioCliente.save(cliente);
    }

}