
INSERT INTO perfil (id_perfil, telefono) VALUES (1, '56912345678');
INSERT INTO perfil (id_perfil, telefono) VALUES (2, '56987654321');
INSERT INTO perfil (id_perfil, telefono) VALUES (3, '56911223344');

INSERT INTO tipo_cliente (id, categoria_cliente, fecha_registro, puntos_minimos) VALUES (1, 'REGULAR', DATE '2024-01-10', 100);
INSERT INTO tipo_cliente (id, categoria_cliente, fecha_registro, puntos_minimos) VALUES (2, 'VIP', DATE '2023-06-15', 500);
INSERT INTO tipo_cliente (id, categoria_cliente, fecha_registro, puntos_minimos) VALUES (3, 'NUEVO', DATE '2025-03-20', 0);


INSERT INTO cliente (rut, dv, nombre_cliente, fecha_nacimiento, papellido, mapellido, puntos, tipo_cliente_id)
VALUES (12345678, '9', 'Ana', DATE '1990-05-12', 'Torres', 'Gomez', 0, 1);
INSERT INTO cliente (rut, dv, nombre_cliente, fecha_nacimiento, papellido, mapellido, puntos, tipo_cliente_id)
VALUES (23456789, '0', 'Luis', DATE '1985-08-23', 'Perez', 'Silva', 0, 2);
INSERT INTO cliente (rut, dv, nombre_cliente, fecha_nacimiento, papellido, mapellido, puntos, tipo_cliente_id)
VALUES (34567890, '1', 'Maria', DATE '1995-11-30', 'Soto', 'Rojas', 0, 3);


INSERT INTO usuario (id_usuario, email, password, rol, perfil_id_perfil, usuario_id_usuario)
VALUES (1, 'ana@correo.com', 'Pass1234', 'CLIENTE', 1, 12345678);
INSERT INTO usuario (id_usuario, email, password, rol, perfil_id_perfil, usuario_id_usuario)
VALUES (2, 'luis@correo.com', 'Pass5678', 'CLIENTE', 2, 23456789);
INSERT INTO usuario (id_usuario, email, password, rol, perfil_id_perfil, usuario_id_usuario)
VALUES (3, 'admin@correo.com', 'Admin001', 'ADMIN', 3, 34567890);