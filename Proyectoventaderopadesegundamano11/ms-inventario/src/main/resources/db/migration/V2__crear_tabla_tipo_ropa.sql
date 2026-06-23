CREATE TABLE tipo_ropa (
                           id_ropa                NUMBER(8)     NOT NULL,
                           diseno                 NVARCHAR2(50) NOT NULL,
                           estilo                 VARCHAR2(15)  NOT NULL,
                           color                  VARCHAR2(20)  NOT NULL,
                           composicion            VARCHAR2(50)  NOT NULL,
                           detalles               VARCHAR2(20)  NOT NULL,
                           tipo_prenda            VARCHAR2(30)  NOT NULL,
                           genero                 VARCHAR2(8)   NOT NULL,
                           talla                  NVARCHAR2(15) NOT NULL,
                           marca                  NVARCHAR2(30) NOT NULL,
                           estado_prenda          VARCHAR2(18)  NOT NULL,
                           categoria_id_categoria NUMBER(8)     NOT NULL,
                           CONSTRAINT tipo_ropa_pk PRIMARY KEY (id_ropa),
                           CONSTRAINT tipo_ropa_categoria_fk FOREIGN KEY (categoria_id_categoria)
                               REFERENCES categoria (id_categoria)
);