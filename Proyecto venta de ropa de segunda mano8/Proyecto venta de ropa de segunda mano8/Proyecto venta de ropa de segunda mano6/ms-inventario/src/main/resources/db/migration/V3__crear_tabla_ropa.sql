CREATE TABLE ropa (
                      id_ropa           NVARCHAR2(8)  NOT NULL,
                      cuidados          VARCHAR2(80)  NOT NULL,
                      descripcion       NVARCHAR2(80) NOT NULL,
                      tipo_ropa_id_ropa NUMBER(8)     NOT NULL,
                      CONSTRAINT ropa_pk PRIMARY KEY (id_ropa),
                      CONSTRAINT ropa_tipo_ropa_fk FOREIGN KEY (tipo_ropa_id_ropa)
                          REFERENCES tipo_ropa (id_ropa)
);