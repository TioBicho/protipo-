package com.e_commerce.ms_envios.Config;

import com.e_commerce.ms_envios.Modelo.ModeloEnvios;
import com.e_commerce.ms_envios.Modelo.ModeloRegion;
import com.e_commerce.ms_envios.Repositorio.RepositorioEnvios;
import com.e_commerce.ms_envios.Repositorio.RepositorioRegion;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataFakerEnvios implements CommandLineRunner {

    private final RepositorioEnvios repositorioEnvios;
    private final RepositorioRegion repositorioRegion;

    public DataFakerEnvios(RepositorioEnvios repositorioEnvios, RepositorioRegion repositorioRegion) {
        this.repositorioEnvios = repositorioEnvios;
        this.repositorioRegion = repositorioRegion;
    }

    @Override
    public void run(String... args) throws Exception {

        if (repositorioRegion.count() == 0 && repositorioEnvios.count() == 0) {
            Faker faker = new Faker();
            System.out.println("📦 Sembrando base de datos de Envíos y Regiones...");


            String[] nombresRegiones = {"Valparaíso", "Metropolitana", "Biobío", "Coquimbo", "Araucanía"};
            for (String nombre : nombresRegiones) {
                ModeloRegion region = new ModeloRegion();
                region.setNombreRegion(nombre);
                repositorioRegion.save(region);
            }


            String[] transportistas = {"Chilexpress", "Starken", "Blue Express", "Correos de Chile"};
            String[] estados = {"PENDIENTE", "EN_TRANSITO", "ENTREGADO", "DEVUELTO"};


            for (int i = 1; i <= 15; i++) {
                ModeloEnvios envio = new ModeloEnvios();


                String direccion = faker.address().fullAddress();
                envio.setDireccionDestino(direccion.length() > 150 ? direccion.substring(0, 150) : direccion);

                envio.setTransportista(transportistas[faker.random().nextInt(transportistas.length)]);


                envio.setNumeroSeguimiento(faker.bothify("TRK-#########"));


                envio.setEstado(estados[faker.random().nextInt(estados.length)]);


                envio.setFechaCreacion(LocalDate.now().minusDays(faker.number().numberBetween(0, 30)));


                envio.setVentasIdVenta((long) i);

                repositorioEnvios.save(envio);
            }

            System.out.println("✅ ¡Regiones y Envíos generados con éxito en Oracle!");
        } else {
            System.out.println("👍 La BD Envíos ya tiene datos, saltando el DataFaker.");
        }
    }
}