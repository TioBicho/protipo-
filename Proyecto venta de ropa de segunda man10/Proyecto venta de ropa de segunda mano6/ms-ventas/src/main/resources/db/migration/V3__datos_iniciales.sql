INSERT INTO ventas (id_venta, boleta, carrito, cliente_rut, empleado_id_empleado) VALUES (1, 'B-2025-001', 1, 12345678, 1);
INSERT INTO ventas (id_venta, boleta, carrito, cliente_rut, empleado_id_empleado) VALUES (2, 'B-2025-002', 2, 23456789, 2);
INSERT INTO ventas (id_venta, boleta, carrito, cliente_rut, empleado_id_empleado) VALUES (3, 'B-2025-003', 3, 34567890, 3);

INSERT INTO detalle_venta (id_detalle_venta, precio_original, descuento_aplicado, ropa_id_ropa, ventas_id_venta) VALUES (1, 15000, 1500, 'R001', 1);
INSERT INTO detalle_venta (id_detalle_venta, precio_original, descuento_aplicado, ropa_id_ropa, ventas_id_venta) VALUES (2, 25000, 5000, 'R002', 2);
INSERT INTO detalle_venta (id_detalle_venta, precio_original, descuento_aplicado, ropa_id_ropa, ventas_id_venta) VALUES (3, 35000, 3500, 'R003', 3);