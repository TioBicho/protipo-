INSERT INTO categoria (id_categoria, nombre_categoria) VALUES (1, 'Ropa M');
INSERT INTO categoria (id_categoria, nombre_categoria) VALUES (2, 'Ropa H');
INSERT INTO categoria (id_categoria, nombre_categoria) VALUES (3, 'Accesor');

INSERT INTO tipo_ropa (id_ropa, diseno, estilo, color, composicion, detalles, tipo_prenda, genero, talla, marca, estado_prenda, categoria_id_categoria) VALUES (1, 'Liso', 'Casual', 'Azul', '100% Algodon', 'Sin bolsillos', 'Polera', 'Femeni', 'M', 'Zara', 'Bueno', 1);
INSERT INTO tipo_ropa (id_ropa, diseno, estilo, color, composicion, detalles, tipo_prenda, genero, talla, marca, estado_prenda, categoria_id_categoria) VALUES (2, 'Rayado', 'Formal', 'Negro', '100% Algodon', 'Con botones', 'Camisa', 'Masculi', 'L', 'H&M', 'Muy bueno', 2);
INSERT INTO tipo_ropa (id_ropa, diseno, estilo, color, composicion, detalles, tipo_prenda, genero, talla, marca, estado_prenda, categoria_id_categoria) VALUES (3, 'Estampado', 'Deportivo', 'Blanco', 'Poliester', 'Con capucha', 'Poleron', 'Unisex', 'XL', 'Nike', 'Como nuevo', 2);

INSERT INTO ropa (id_ropa, cuidados, descripcion, tipo_ropa_id_ropa) VALUES ('R001', 'Lavar con agua fria', 'Polera azul casual talla M', 1);
INSERT INTO ropa (id_ropa, cuidados, descripcion, tipo_ropa_id_ropa) VALUES ('R002', 'Limpieza en seco', 'Camisa formal negra talla L', 2);
INSERT INTO ropa (id_ropa, cuidados, descripcion, tipo_ropa_id_ropa) VALUES ('R003', 'Prenda delicada', 'Poleron deportivo blanco XL', 3);

INSERT INTO stock (id_stock, cantidad, estado_inventario, ropa_id_ropa, sucursal_id_sucursal) VALUES (1, 10, 'DISPONIBLE', 'R001', 1);
INSERT INTO stock (id_stock, cantidad, estado_inventario, ropa_id_ropa, sucursal_id_sucursal) VALUES (2, 5, 'DISPONIBLE', 'R002', 2);
INSERT INTO stock (id_stock, cantidad, estado_inventario, ropa_id_ropa, sucursal_id_sucursal) VALUES (3, 3, 'BAJO_STOCK', 'R003', 3);