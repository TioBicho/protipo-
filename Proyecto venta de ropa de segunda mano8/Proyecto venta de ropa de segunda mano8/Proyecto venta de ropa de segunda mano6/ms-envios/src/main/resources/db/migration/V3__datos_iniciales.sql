INSERT INTO region (id_region, nombre_region) VALUES (1, 'Metropolitana');
INSERT INTO region (id_region, nombre_region) VALUES (2, 'Antofagasta');
INSERT INTO region (id_region, nombre_region) VALUES (3, 'Biobio');

INSERT INTO envio (id_envio, direccion_destino, transportista, numero_seguimiento, estado, fecha_creacion, ventas_id_venta) VALUES (1, 'Av. Providencia 123, Santiago', 'Starken', 'STK-001', 'EN_CAMINO', DATE '2025-05-01', 1);
INSERT INTO envio (id_envio, direccion_destino, transportista, numero_seguimiento, estado, fecha_creacion, ventas_id_venta) VALUES (2, 'Calle Norte 456, Antofagasta', 'Chilexpress', 'CHX-002', 'ENTREGADO', DATE '2025-05-03', 2);
INSERT INTO envio (id_envio, direccion_destino, transportista, numero_seguimiento, estado, fecha_creacion, ventas_id_venta) VALUES (3, 'Pasaje Sur 789, Concepcion', 'Correos', 'COR-003', 'PENDIENTE', DATE '2025-05-05', 3);

