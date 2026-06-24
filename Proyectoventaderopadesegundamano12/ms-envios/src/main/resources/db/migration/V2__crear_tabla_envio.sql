CREATE TABLE envio (
                       id_envio           NUMBER(8)      NOT NULL,
                       direccion_destino  NVARCHAR2(150) NOT NULL,
                       transportista      VARCHAR2(50)   NOT NULL,
                       numero_seguimiento NVARCHAR2(50)  NOT NULL,
                       estado             VARCHAR2(50)   NOT NULL,
                       fecha_creacion     DATE           NOT NULL,
                       ventas_id_venta    NUMBER(12)     NOT NULL,
                       CONSTRAINT envio_pk PRIMARY KEY (id_envio),
                       CONSTRAINT envio_ventas_unique UNIQUE (ventas_id_venta)
);