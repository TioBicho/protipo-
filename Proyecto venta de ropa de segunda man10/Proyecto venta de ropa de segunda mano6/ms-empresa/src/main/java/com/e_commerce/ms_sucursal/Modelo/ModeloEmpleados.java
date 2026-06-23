package com.e_commerce.ms_sucursal.Modelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Empleado")
public class ModeloEmpleados {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empleado_seq_gen")
    @SequenceGenerator(name = "empleado_seq_gen", sequenceName = "EMPLEADO_SEQ", allocationSize = 1)
    @Column(name = "id_empleado")
    private Long id;

    @Column(name = "run", nullable = false)
    private Integer run;

    @Column(name = "dv", nullable = false, length = 1)
    private String dv;
    @Column(name = "nombre", nullable = false, length = 15)
    private String nombre;

    @Column(name = "papellido", nullable = false, length = 8)
    private String apellidoPaterno;

    @Column(name = "mapellido", nullable = false, length = 8)
    private String apellidoMaterno;

    @Column(name = "fecha_contrato", nullable = false)
    private LocalDate fechaContrato;

    @Column(name = "cargo", nullable = false, length = 15)
    private String cargo;

    @Column(name = "Usuario_id_usuario", nullable = false)
    private Long usuarioId;

    @Column(name = "Sucursal_id_sucursal", nullable = false)
    private Long sucursalId;
}