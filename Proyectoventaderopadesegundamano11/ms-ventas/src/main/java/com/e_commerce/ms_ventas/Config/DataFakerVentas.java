package com.e_commerce.ms_ventas.Config;

import com.e_commerce.ms_ventas.Modelo.ModeloDetalleVenta;
import com.e_commerce.ms_ventas.Modelo.ModeloVentas;
import com.e_commerce.ms_ventas.Repositorio.RepositorioVentas;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataFakerVentas implements CommandLineRunner {

    private final RepositorioVentas repositorioVentas;

    public DataFakerVentas(RepositorioVentas repositorioVentas) {
        this.repositorioVentas = repositorioVentas;
    }

    @Override
    public void run(String... args) throws Exception {

        if (repositorioVentas.count() == 0) {
            Faker faker = new Faker();
            System.out.println("💳 Sembrando base de datos de Ventas y Detalles...");

            for (int i = 1; i <= 15; i++) {
                ModeloVentas venta = new ModeloVentas();


                venta.setBoleta(faker.bothify("BOL-########-????").toUpperCase());


                venta.setCarrito(faker.number().numberBetween(100, 999));


                venta.setClienteRut(faker.number().numberBetween(10000000, 25000000));


                venta.setEmpleadoId((long) faker.number().numberBetween(1, 16));


                int cantidadPrendas = faker.number().numberBetween(1, 4);

                for (int j = 0; j < cantidadPrendas; j++) {
                    ModeloDetalleVenta detalle = new ModeloDetalleVenta();

                    int precio = faker.number().numberBetween(5000, 45000);
                    detalle.setPrecioOriginal(precio);


                    detalle.setDescuentoAplicado(faker.number().numberBetween(0, 2000));


                    detalle.setRopaId(faker.bothify("PR-#####"));


                    detalle.setVenta(venta);


                    venta.getDetalles().add(detalle);
                }


                repositorioVentas.save(venta);
            }

            System.out.println("✅ ¡Ventas y Detalles generados con éxito en Oracle!");
        } else {
            System.out.println("👍 La BD Ventas ya tiene datos, saltando el DataFaker.");
        }
    }
}