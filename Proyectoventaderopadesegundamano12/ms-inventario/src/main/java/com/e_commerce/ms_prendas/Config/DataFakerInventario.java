package com.e_commerce.ms_prendas.Config;

import com.e_commerce.ms_prendas.Modelo.*;
import com.e_commerce.ms_prendas.Repositorio.*;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataFakerInventario implements CommandLineRunner {

    private final RepositorioCategoria repositorioCategoria;
    private final RepositorioTipoRopa repositorioTipoRopa;
    private final RepositorioPrenda repositorioPrenda;
    private final RepositorioStock repositorioStock;

    public DataFakerInventario(RepositorioCategoria repositorioCategoria,
                               RepositorioTipoRopa repositorioTipoRopa,
                               RepositorioPrenda repositorioPrenda,
                               RepositorioStock repositorioStock) {
        this.repositorioCategoria = repositorioCategoria;
        this.repositorioTipoRopa = repositorioTipoRopa;
        this.repositorioPrenda = repositorioPrenda;
        this.repositorioStock = repositorioStock;
    }

    @Override
    public void run(String... args) throws Exception {

        if (repositorioCategoria.count() == 0 && repositorioPrenda.count() == 0) {
            Faker faker = new Faker();
            System.out.println("👕 Sembrando base de datos de Inventario de Ropa...");


            String[] nombresCat = {"Parte Superior", "Parte Inferior", "Abrigos", "Calzado", "Accesorios"};
            List<ModeloCategoria> categorias = new ArrayList<>();
            for (String nombre : nombresCat) {
                ModeloCategoria cat = new ModeloCategoria();
                cat.setNombre(nombre);
                categorias.add(repositorioCategoria.save(cat));
            }

            String[] generos = {"MUJER", "HOMBRE", "UNISEX"};
            String[] tallas = {"XS", "S", "M", "L", "XL", "XXL"};
            String[] estadosRopa = {"NUEVO", "COMO NUEVO", "USADO"};
            String[] letrasDiseno = {"L", "E", "B", "R"};
            String[] cuidadosPermitidos = {
                    "Lavar a mano",
                    "Lavar con agua fria",
                    "No usar secadora",
                    "Limpieza en seco",
                    "Prenda delicada"
            };


            for (int i = 0; i < 15; i++) {


                ModeloTipoRopa tipo = new ModeloTipoRopa();

                tipo.setDiseno(faker.options().option(letrasDiseno));

                String estilo = faker.commerce().department();
                tipo.setEstilo(estilo.length() > 15 ? estilo.substring(0, 15) : estilo);

                String color = faker.color().name();
                tipo.setColor(color.length() > 20 ? color.substring(0, 20) : color);

                String composicion = faker.commerce().material();
                tipo.setComposicion(composicion.length() > 50 ? composicion.substring(0, 50) : composicion);

                tipo.setDetalles("Sin detalles extra"); // Límite 20

                String nombrePrenda = faker.commerce().productName();
                tipo.setTipoPrenda(nombrePrenda.length() > 30 ? nombrePrenda.substring(0, 30) : nombrePrenda);

                tipo.setGenero(faker.options().option(generos));
                tipo.setTalla(faker.options().option(tallas));

                String marca = faker.brand().sport();
                tipo.setMarca(marca.length() > 30 ? marca.substring(0, 30) : marca);

                tipo.setEstadoPrenda(faker.options().option(estadosRopa));


                ModeloCategoria catAsignada = categorias.get(faker.random().nextInt(categorias.size()));
                tipo.setCategoriaId(catAsignada.getId());

                ModeloTipoRopa tipoGuardado = repositorioTipoRopa.save(tipo);


                ModeloPrenda prenda = new ModeloPrenda();


                String idPersonalizado = faker.bothify("PR-#####");
                prenda.setId(idPersonalizado);

                prenda.setCuidados(faker.options().option(cuidadosPermitidos));

                String desc = "Prenda vintage " + tipoGuardado.getTipoPrenda() + " seleccionada a mano.";
                prenda.setDescripcion(desc.length() > 80 ? desc.substring(0, 80) : desc);

                prenda.setTipoRopa(tipoGuardado);

                ModeloPrenda prendaGuardada = repositorioPrenda.save(prenda);


                ModeloStock stock = new ModeloStock();
                stock.setCantidad(faker.number().numberBetween(0, 50));
                stock.setEstadoInventario(stock.getCantidad() > 0 ? "DISPONIBLE" : "AGOTADO");


                stock.setRopaIdRopa(prendaGuardada.getId());


                stock.setSucursalIdSucursal((long) faker.number().numberBetween(1, 4));

                repositorioStock.save(stock);
            }

            System.out.println("✅ ¡Catálogo, Prendas y Stock generados con éxito en Oracle!");
        } else {
            System.out.println("👍 La BD Inventario ya tiene datos, saltando el DataFaker.");
        }
    }
}