CREATE TABLE detalle_venta (
                               id_detalle_venta   NUMBER(15)   NOT NULL,
                               precio_original    NUMBER(12)   NOT NULL,
                               descuento_aplicado NUMBER(12)   NOT NULL,
                               ropa_id_ropa       NVARCHAR2(8) NOT NULL,
                               ventas_id_venta    NUMBER(12)   NOT NULL,
                               CONSTRAINT detalle_venta_pk PRIMARY KEY (id_detalle_venta),
                               CONSTRAINT detalle_venta_ventas_fk FOREIGN KEY (ventas_id_venta)
                                   REFERENCES ventas (id_venta)
);