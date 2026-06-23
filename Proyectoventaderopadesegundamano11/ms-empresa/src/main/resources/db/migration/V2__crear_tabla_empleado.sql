CREATE TABLE empleado (
                          id_empleado          NUMBER(8)    NOT NULL,
                          run                  NUMBER(8)    NOT NULL,
                          dv                   VARCHAR2(1)  NOT NULL,
                          nombre               VARCHAR2(15) NOT NULL,
                          papellido            VARCHAR2(8)  NOT NULL,
                          mapellido            VARCHAR2(8)  NOT NULL,
                          fecha_contrato       DATE         NOT NULL,
                          cargo                VARCHAR2(15) NOT NULL,
                          usuario_id_usuario   NUMBER(8)    NOT NULL,
                          sucursal_id_sucursal NUMBER(8)    NOT NULL,
                          CONSTRAINT empleado_pk PRIMARY KEY (id_empleado),
                          CONSTRAINT empleado_sucursal_fk FOREIGN KEY (sucursal_id_sucursal)
                              REFERENCES sucursal (id_sucursal)
);