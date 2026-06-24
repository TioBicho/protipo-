CREATE TABLE sucursal (
                          id_sucursal     NUMBER(8)     NOT NULL,
                          nombre_sucursal NVARCHAR2(15) NOT NULL,
                          ciudad          VARCHAR2(20)  NOT NULL,
                          CONSTRAINT sucursal_pk PRIMARY KEY (id_sucursal)
);