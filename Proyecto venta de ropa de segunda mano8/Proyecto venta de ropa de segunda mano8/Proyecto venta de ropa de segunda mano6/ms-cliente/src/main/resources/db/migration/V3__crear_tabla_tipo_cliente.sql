CREATE TABLE tipo_cliente (
                              id                NUMBER(5)   NOT NULL,
                              categoria_cliente VARCHAR2(8) NOT NULL,
                              fecha_registro    DATE        NOT NULL,
                              puntos_minimos    NUMBER(9)   DEFAULT 0 NOT NULL,
                              CONSTRAINT tipo_cliente_pk PRIMARY KEY (id)
);