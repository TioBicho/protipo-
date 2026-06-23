INSERT INTO sucursal (id_sucursal, nombre_sucursal, ciudad) VALUES (1, 'Suc Centro', 'Santiago');
INSERT INTO sucursal (id_sucursal, nombre_sucursal, ciudad) VALUES (2, 'Suc Norte', 'Antofagasta');
INSERT INTO sucursal (id_sucursal, nombre_sucursal, ciudad) VALUES (3, 'Suc Sur', 'Concepcion');

INSERT INTO empleado (id_empleado, run, dv, nombre, papellido, mapellido, fecha_contrato, cargo, usuario_id_usuario, sucursal_id_sucursal) VALUES (1, 11111111, 'K', 'Carlos', 'Ramirez', 'Vega', DATE '2022-03-01', 'Vendedor', 1, 1);
INSERT INTO empleado (id_empleado, run, dv, nombre, papellido, mapellido, fecha_contrato, cargo, usuario_id_usuario, sucursal_id_sucursal) VALUES (2, 22222222, '5', 'Patricia', 'Mora', 'Diaz', DATE '2021-07-15', 'Cajera', 2, 2);
INSERT INTO empleado (id_empleado, run, dv, nombre, papellido, mapellido, fecha_contrato, cargo, usuario_id_usuario, sucursal_id_sucursal) VALUES (3, 33333333, '3', 'Sebastian', 'Fuentes', 'Castro', DATE '2023-01-10', 'Gerente', 3, 3);

INSERT INTO nomina (id_nomina, fecha_emision, sueldo_base, bonos, descuentos_legales, sueldo_liquido, empleado_id_empleado) VALUES (1, DATE '2025-05-31', 600000, 50000, 80000, 570000, 1);
INSERT INTO nomina (id_nomina, fecha_emision, sueldo_base, bonos, descuentos_legales, sueldo_liquido, empleado_id_empleado) VALUES (2, DATE '2025-05-31', 550000, 30000, 75000, 505000, 2);
INSERT INTO nomina (id_nomina, fecha_emision, sueldo_base, bonos, descuentos_legales, sueldo_liquido, empleado_id_empleado) VALUES (3, DATE '2025-05-31', 900000, 100000, 120000, 880000, 3);