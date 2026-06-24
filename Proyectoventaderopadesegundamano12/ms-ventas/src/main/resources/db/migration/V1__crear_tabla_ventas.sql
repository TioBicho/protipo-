CREATE TABLE ventas (
                        id_venta             NUMBER(12)   NOT NULL,
                        boleta               VARCHAR2(30) NOT NULL,
                        carrito              NUMBER(8)    NOT NULL,
                        cliente_rut          NUMBER(8)    NOT NULL,
                        empleado_id_empleado NUMBER(8)    NOT NULL,
                        CONSTRAINT ventas_pk PRIMARY KEY (id_venta)
);