CREATE TABLE cliente (
                         rut NUMBER(8) NOT NULL,
                         dv VARCHAR2(1) NOT NULL,
                         nombre_cliente VARCHAR2(9) NOT NULL,
                         fecha_nacimiento DATE NOT NULL,
                         papellido VARCHAR2(15) NOT NULL,
                         mapellido VARCHAR2(15) NOT NULL,
                         puntos NUMBER(9) NOT NULL, -- Aquí recuperamos la billetera de puntos
                         tipo_cliente_id NUMBER(5) NOT NULL,
                         CONSTRAINT cliente_pk PRIMARY KEY (rut),
                         CONSTRAINT cliente_tipo_cliente_fk FOREIGN KEY (tipo_cliente_id)
                             REFERENCES tipo_cliente (id)

);