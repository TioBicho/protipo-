package com.e_commerce.ms_sucursal.Config;

import com.e_commerce.ms_sucursal.Modelo.ModeloEmpleados;
import com.e_commerce.ms_sucursal.Modelo.ModeloNomina;
import com.e_commerce.ms_sucursal.Modelo.ModeloSucursal;
import com.e_commerce.ms_sucursal.Repositorio.RepositorioEmpleado;
import com.e_commerce.ms_sucursal.Repositorio.RepositorioNomina;
import com.e_commerce.ms_sucursal.Repositorio.RepositorioSucursal;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataFakerEmpresa implements CommandLineRunner {

    private final RepositorioSucursal repositorioSucursal;
    private final RepositorioEmpleado repositorioEmpleados;
    private final RepositorioNomina repositorioNomina;

    public DataFakerEmpresa(RepositorioSucursal repositorioSucursal,
                            RepositorioEmpleado repositorioEmpleados,
                            RepositorioNomina repositorioNomina) {
        this.repositorioSucursal = repositorioSucursal;
        this.repositorioEmpleados = repositorioEmpleados;
        this.repositorioNomina = repositorioNomina;
    }

    @Override
    public void run(String... args) throws Exception {


        if (repositorioSucursal.count() == 0 && repositorioEmpleados.count() == 0) {
            Faker faker = new Faker();
            System.out.println("🏢 Sembrando base de datos de Empresa (Sucursales, Empleados y Nóminas)...");


            List<ModeloSucursal> sucursalesGuardadas = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                ModeloSucursal sucursal = new ModeloSucursal();

                String nombreSuc = "Suc. " + faker.address().cityName();
                sucursal.setNombreSucursal(nombreSuc.length() > 15 ? nombreSuc.substring(0, 15) : nombreSuc);

                String ciudad = faker.address().city();
                sucursal.setCiudad(ciudad.length() > 20 ? ciudad.substring(0, 20) : ciudad);

                sucursal.setRegionId((long) faker.number().numberBetween(1, 16));

                sucursalesGuardadas.add(repositorioSucursal.save(sucursal));
            }


            for (int i = 0; i < 15; i++) {
                ModeloEmpleados empleado = new ModeloEmpleados();

                empleado.setRun(faker.number().numberBetween(10000000, 25000000));
                String[] dvs = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "K"};
                empleado.setDv(dvs[faker.random().nextInt(dvs.length)]);

                String nombre = faker.name().firstName();
                empleado.setNombre(nombre.length() > 15 ? nombre.substring(0, 15) : nombre);

                String apePaterno = faker.name().lastName();
                empleado.setApellidoPaterno(apePaterno.length() > 8 ? apePaterno.substring(0, 8) : apePaterno);

                String apeMaterno = faker.name().lastName();
                empleado.setApellidoMaterno(apeMaterno.length() > 8 ? apeMaterno.substring(0, 8) : apeMaterno);

                empleado.setFechaContrato(LocalDate.now().minusMonths(faker.number().numberBetween(1, 60)));

                String cargo = faker.job().position();
                empleado.setCargo(cargo.length() > 15 ? cargo.substring(0, 15) : cargo);

                empleado.setUsuarioId((long) faker.number().numberBetween(1, 16));

                ModeloSucursal sucursalAsignada = sucursalesGuardadas.get(faker.random().nextInt(sucursalesGuardadas.size()));
                empleado.setSucursalId(sucursalAsignada.getId());

                ModeloEmpleados empleadoGuardado = repositorioEmpleados.save(empleado);


                ModeloNomina nomina = new ModeloNomina();

                long sueldoBase = faker.number().numberBetween(500000L, 1500000L);
                long bonos = faker.number().numberBetween(0L, 200000L);
                long descuentos = faker.number().numberBetween(50000L, 150000L);

                nomina.setSueldoBase(sueldoBase);
                nomina.setBonos(bonos);
                nomina.setDescuentos(descuentos);
                nomina.setSueldoLiquido(sueldoBase + bonos - descuentos);
                nomina.setFechaEmision(LocalDate.now());


                nomina.setEmpleadoId(empleadoGuardado.getId());

                repositorioNomina.save(nomina);
            }

            System.out.println("✅ ¡Sucursales, Empleados y Nóminas generadas con éxito en Oracle!");
        } else {
            System.out.println("👍 La BD Empresa ya tiene datos, saltando el DataFaker.");
        }
    }
}