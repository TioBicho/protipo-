package com.e_commerce.ms_cliente.Servicio;

import com.e_commerce.ms_cliente.Cliente.PerfilCliente;
import com.e_commerce.ms_cliente.DTO.RegistroRequestDTO;
import com.e_commerce.ms_cliente.DTO.UsuarioDTO;
import com.e_commerce.ms_cliente.Modelo.ModeloCliente;
import com.e_commerce.ms_cliente.Modelo.ModeloPerfil;
import com.e_commerce.ms_cliente.Modelo.ModeloUsuario;
import com.e_commerce.ms_cliente.Repositorio.RepositorioCliente;
import com.e_commerce.ms_cliente.Repositorio.RepositorioPerfil;
import com.e_commerce.ms_cliente.Repositorio.RepositorioUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServicioUsuario {


    @Autowired
    private RepositorioUsuario repository;

    @Autowired
    private RepositorioPerfil repositorioPerfil;

    @Autowired
    private RepositorioCliente repositorioCliente;

    public ModeloUsuario guardarUsuario(Long rutCliente, RegistroRequestDTO dto) {
        if (repository.findByEmail(dto.getUsuario().getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe una cuenta registrada con este correo electrónico.");
        }

        ModeloCliente cliente = repositorioCliente.findById(rutCliente)
                .orElseThrow(() -> new RuntimeException("El cliente con RUT " + rutCliente + " no existe."));

        ModeloPerfil perfil = new ModeloPerfil();
        perfil.setTelefono(dto.getPerfil().getTelefono());
        perfil = repositorioPerfil.save(perfil);

        ModeloUsuario usuario = new ModeloUsuario();
        usuario.setEmail(dto.getUsuario().getEmail());
        usuario.setPassword(dto.getUsuario().getPassword());
        usuario.setRol(dto.getUsuario().getRol());
        usuario.setPerfil(perfil);
        usuario = repository.save(usuario);

        cliente.getUsuarios().add(usuario);
        repositorioCliente.save(cliente);

        return usuario;
    }

    public List<ModeloUsuario> obtenerTodos() {
        log.info("Solicitando el listado general de usuarios de la plataforma.");
        return repository.findAll();
    }

    public ModeloUsuario obtenerPorId(Long id) {
        log.info("Buscando cuenta con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario con ID " + id + " no está registrado en el sistema."));
    }


    public List<ModeloUsuario> listarPorRol(String rol) {
        log.info("Filtrando cuentas bajo el rol operativo: {}", rol);
        return repository.buscarUsuariosPorRol(rol);
    }
    public void eliminarUsuario(Long id) {
        log.info("Iniciando proceso de eliminación para el usuario ID: {}", id);


        ModeloUsuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: El usuario con ID " + id + " no existe."));

        repository.delete(usuario);
        log.info("Usuario ID {} eliminado exitosamente de Oracle Cloud.", id);
    }
    public ModeloUsuario actualizarUsuario(Long id, RegistroRequestDTO dto) {
        log.info("Iniciando proceso de actualización para el usuario ID: {}", id);

        ModeloUsuario usuarioActual = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: El usuario con ID " + id + " no existe."));


        repository.findByEmail(dto.getUsuario().getEmail()).ifPresent(otroUsuario -> {
            if (!otroUsuario.getId().equals(id)) { // Si el ID es diferente, es de otra persona
                log.error("Validación fallida: El email ya está ocupado");
                throw new RuntimeException("El correo electrónico informado ya se encuentra registrado en otra cuenta.");
            }
        });
        usuarioActual.setEmail(dto.getUsuario() .getEmail());
        usuarioActual.setPassword(dto.getUsuario().getPassword());
        usuarioActual.setRol(dto.getUsuario().getRol());


        if (dto.getPerfil() != null && dto.getPerfil().getTelefono() != null) {
            ModeloPerfil perfil = usuarioActual.getPerfil();
            if (perfil != null) {
                perfil.setTelefono(dto.getPerfil().getTelefono());
                repositorioPerfil.save(perfil);
            }
        }


        return repository.save(usuarioActual);
    };
}