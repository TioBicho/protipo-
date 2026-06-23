CREATE TABLE nomina (
                        id_nomina            NUMBER(8)  NOT NULL,
                        fecha_emision        DATE       NOT NULL,
                        sueldo_base          NUMBER(15) NOT NULL,
                        bonos                NUMBER(12) NOT NULL,
                        descuentos_legales   NUMBER(8)  NOT NULL,
                        sueldo_liquido       NUMBER(15) NOT NULL,
                        empleado_id_empleado NUMBER(8)  NOT NULL,
                        CONSTRAINT nomina_pk PRIMARY KEY (id_nomina),
                        CONSTRAINT nomina_empleado_fk FOREIGN KEY (empleado_id_empleado)
                            REFERENCES empleado (id_empleado)
);