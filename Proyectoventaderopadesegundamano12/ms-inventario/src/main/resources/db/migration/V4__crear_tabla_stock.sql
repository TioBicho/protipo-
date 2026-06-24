CREATE TABLE stock (
                       id_stock             NUMBER(8)    NOT NULL,
                       cantidad             NUMBER(5)    NOT NULL,
                       estado_inventario    VARCHAR2(50) NOT NULL,
                       ropa_id_ropa         NVARCHAR2(8) NOT NULL,
                       sucursal_id_sucursal NUMBER(8)    NOT NULL,
                       CONSTRAINT stock_pk PRIMARY KEY (id_stock),
                       CONSTRAINT stock_ropa_fk FOREIGN KEY (ropa_id_ropa)
                           REFERENCES ropa (id_ropa)
);