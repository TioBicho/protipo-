package com.e_commerce.ms_cliente.Config;

import com.e_commerce.ms_cliente.Modelo.ModeloCliente;
import com.e_commerce.ms_cliente.Modelo.ModeloPerfil;
import com.e_commerce.ms_cliente.Modelo.ModeloTipoCliente;
import com.e_commerce.ms_cliente.Modelo.ModeloUsuario;
import com.e_commerce.ms_cliente.Repositorio.RepositorioCliente;
import com.e_commerce.ms_cliente.Repositorio.RepositorioPerfil;
import com.e_commerce.ms_cliente.Repositorio.RepositorioTipoCliente;
import com.e_commerce.ms_cliente.Repositorio.RepositorioUsuario;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataFakerCliente implements CommandLineRunner {

    private final RepositorioCliente repositorioCliente;
    private final RepositorioTipoCliente repositorioTipoCliente;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioPerfil repositorioPerfil;

    public DataFakerCliente(RepositorioCliente repositorioCliente,
                            RepositorioTipoCliente repositorioTipoCliente,
                            RepositorioUsuario repositorioUsuario,
                            RepositorioPerfil repositorioPerfil) {
        this.repositorioCliente = repositorioCliente;
        this.repositorioTipoCliente = repositorioTipoCliente;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioPerfil = repositorioPerfil;
    }

    @Override
    public void run(String... args) throws Exception {

        if (repositorioTipoCliente.count() == 0 && repositorioCliente.count() == 0) {
            Faker faker = new Faker();
            System.out.println("🌱 Sembrando base de datos de Clientes, Usuarios, Perfiles y Tipos...");


            ModeloTipoCliente tipoNormal = new ModeloTipoCliente();
            tipoNormal.setCategoriaCliente("Normal"); // length = 8
            tipoNormal.setFechaRegistro(LocalDate.now());
            tipoNormal.setPuntosMinimos(0);
            repositorioTipoCliente.save(tipoNormal);

            ModeloTipoCliente tipoVIP = new ModeloTipoCliente();
            tipoVIP.setCategoriaCliente("VIP");
            tipoVIP.setFechaRegistro(LocalDate.now());
            tipoVIP.setPuntosMinimos(1000);
            repositorioTipoCliente.save(tipoVIP);

            List<ModeloTipoCliente> tipos = Arrays.asList(tipoNormal, tipoVIP);


            for (int i = 0; i < 15; i++) {

                ModeloPerfil perfil = new ModeloPerfil();

                perfil.setTelefono("569" + faker.number().digits(8));
                ModeloPerfil perfilGuardado = repositorioPerfil.save(perfil);


                ModeloUsuario usuario = new ModeloUsuario();
                String emailBase = faker.internet().emailAddress();

                usuario.setEmail(emailBase.length() > 30 ? emailBase.substring(0, 30) : emailBase);
                usuario.setPassword("PwdFaker2024!");
                usuario.setRol(i % 2 == 0 ? "USER" : "ADMIN");
                usuario.setPerfil(perfilGuardado);
                ModeloUsuario usuarioGuardado = repositorioUsuario.save(usuario);

                ModeloCliente cliente = new ModeloCliente();

                cliente.setRut(faker.number().numberBetween(10000000L, 25000000L));
                String[] dvs = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "K"};
                cliente.setDv(dvs[faker.random().nextInt(dvs.length)]);

                String nombre = faker.name().firstName();
                cliente.setNombreCliente(nombre.length() > 9 ? nombre.substring(0, 9) : nombre);

                String apellidoP = faker.name().lastName();
                cliente.setPapellido(apellidoP.length() > 15 ? apellidoP.substring(0, 15) : apellidoP);

                String apellidoM = faker.name().lastName();
                cliente.setMapellido(apellidoM.length() > 15 ? apellidoM.substring(0, 15) : apellidoM);

                cliente.setFechaNacimiento(LocalDate.now().minusYears(faker.number().numberBetween(18, 60)));
                cliente.setPuntos(faker.number().numberBetween(0, 5000));

                // Asignaciones de llaves foráneas
                cliente.setTipoCliente(tipos.get(faker.random().nextInt(tipos.size())));
                // OJO: Como ModeloCliente tiene una Lista de Usuarios (@OneToMany), hacemos esto:
                cliente.getUsuarios().add(usuarioGuardado);

                repositorioCliente.save(cliente);
            }

            System.out.println("✅ ¡Ecosistema completo generado con éxito en Oracle!");
        } else {
            System.out.println("👍 La BD ya tiene datos, saltando el DataFaker.");
        }
    }
}