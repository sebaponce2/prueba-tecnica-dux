--Motor: MySQL

--Creación de las tablas necesarias siguiendo la estructura de la consigna.
CREATE TABLE rubro(
	id_rubro BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    rubro VARCHAR(30) NOT NULL
);

CREATE TABLE cliente(
	id_cliente BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    cuit DATE NOT NULL
);

CREATE TABLE producto(
	codigo VARCHAR(30) PRIMARY KEY NOT NULL,
    id_rubro BIGINT NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    fecha_creacion DATE NOT NULL,
    FOREIGN KEY (id_rubro) REFERENCES rubro(id_rubro)
);


CREATE TABLE venta(
	id_venta BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    codigo_producto VARCHAR(30) NOT NULL,
    id_cliente BIGINT NOT NULL,
    fecha DATE NOT NULL,
    cantidad BIGINT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    FOREIGN KEY (codigo_producto) REFERENCES producto(codigo),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

--Insert de los datos necesarios para las pruebas.
INSERT INTO rubro(rubro)
VALUES('Libreria'),
('Bazar'),
('Ferreteria'),
('Veterinaria');


INSERT INTO cliente(nombre, apellido, cuit)
VALUES('Juan', 'Perez', CURDATE()),
('Lucas', 'Martinez', DATE_SUB(CURDATE(), INTERVAL 2 MONTH)),
('Lucia', 'Garcia', DATE_SUB(CURDATE(), INTERVAL 1 MONTH)),
('Matias', 'Ramirez', DATE_SUB(CURDATE(), INTERVAL 4 MONTH)),
('Pedro', 'Benitez', DATE_SUB(CURDATE(), INTERVAL 5 MONTH));


INSERT INTO producto(codigo, id_rubro, nombre, fecha_creacion)
VALUES('LIB001', 1, 'Harry Potter 1', CURDATE()),
('LIB002', 1, 'El señor de los anillos', DATE_SUB(CURDATE(), INTERVAL 1 MONTH)),
('LIB003', 1, 'Martin Fierro', CURDATE()),
('LIB004', 1, 'La bella y la bestia', DATE_SUB(CURDATE(), INTERVAL 3 MONTH)),
('BAZ001', 2, 'Sarten', CURDATE()),
('BAZ002', 2, 'Olla', DATE_SUB(CURDATE(), INTERVAL 1 MONTH)),
('BAZ003', 2, 'Colador', CURDATE()),
('BAZ004', 2, 'Cuchillo', DATE_SUB(CURDATE(), INTERVAL 3 MONTH)),
('FER001', 3, 'Lija', CURDATE()),
('FER002', 3, 'Cable', DATE_SUB(CURDATE(), INTERVAL 1 MONTH)),
('FER003', 3, 'Alicate', DATE_SUB(CURDATE(), INTERVAL 3 MONTH)),
('VET001', 4, 'Shampoo gato', CURDATE()),
('VET002', 4, 'Antipulgas', DATE_SUB(CURDATE(), INTERVAL 1 MONTH)),
('VET003', 4, 'Recipiente agua', DATE_SUB(CURDATE(), INTERVAL 3 MONTH));


INSERT INTO venta(codigo_producto, id_cliente, fecha, cantidad, precio_unitario)
VALUES('LIB001', 1, DATE_SUB(CURDATE(), INTERVAL 2 MONTH), 10, 200),
('LIB002', 1, CURDATE(), 10, 300),
('BAZ001', 1, CURDATE(), 15, 400),
('BAZ001', 2, CURDATE(), 15, 400),
('VET002', 2, DATE_SUB(CURDATE(), INTERVAL 3 MONTH), 10, 200),
('FER003', 2, DATE_SUB(CURDATE(), INTERVAL 3 MONTH), 10, 300),
('BAZ002', 3, CURDATE()-3, 15, 500),
('LIB004', 3, DATE_SUB(CURDATE(), INTERVAL 1 MONTH), 5, 500),
('VET001', 3, DATE_SUB(CURDATE(), INTERVAL 4 MONTH), 5, 100),
('BAZ004', 4, CURDATE()-5, 10, 50),
('LIB003', 4, DATE_SUB(CURDATE(), INTERVAL 1 MONTH), 10, 400),
('FER002', 4, DATE_SUB(CURDATE(), INTERVAL 3 MONTH), 20, 500),
('VET002', 5, DATE_SUB(CURDATE(), INTERVAL 4 MONTH), 10, 200),
('FER003', 5, DATE_SUB(CURDATE(), INTERVAL 4 MONTH), 15, 300),
('FER002', 5, DATE_SUB(CURDATE(), INTERVAL 4 MONTH), 10, 500);




--1) Todos los productos del rubro "librería", creados hoy.
SELECT p.*
FROM producto p
JOIN rubro r
ON r.id_rubro = p.id_rubro
WHERE UPPER(r.rubro) = UPPER('Bazar')
AND p.fecha_creacion = CURDATE()


--2) Monto total vendido por cliente (mostrar nombre del cliente y monto).
SELECT c.nombre, SUM(v.cantidad * v.precio_unitario) AS monto FROM venta v
JOIN cliente c
ON c.id_cliente = v.id_cliente
GROUP BY v.id_cliente


--3) Cantidad de ventas por producto
SELECT p.nombre AS nombre_producto, SUM(v.cantidad) AS cant_ventas_producto
FROM venta v
JOIN producto p
ON p.codigo = v.codigo_producto
GROUP BY v.codigo_producto


--4) Cantidad de productos diferentes comprados por cliente en el mes actual.
SELECT v.id_cliente, c.nombre, count(DISTINCT v.codigo_producto) AS cant_prod_diferentes_mes_actual FROM venta v
JOIN cliente c
ON c.id_cliente = v.id_cliente
WHERE MONTH(v.fecha) = MONTH(CURDATE())
GROUP BY v.id_cliente;


--5) Ventas que tienen al menos un producto del rubro "bazar".
SELECT v.*, p.nombre FROM venta v
JOIN producto p
ON p.codigo = v.codigo_producto
JOIN rubro r
ON r.id_rubro = p.id_rubro
WHERE UPPER(rubro) = UPPER('Bazar')


--6) Rubros que no tienen ventas en los últimos 2 meses.
SELECT r.rubro FROM rubro r
WHERE r.id_rubro NOT IN(SELECT DISTINCT p.ID_RUBRO
			            FROM producto p
			            WHERE CODIGO IN(SELECT DISTINCT v.CODIGO_PRODUCTO
						                FROM venta v
 						                WHERE MONTH(v.FECHA) BETWEEN MONTH(CURDATE()) - 2
 						                AND MONTH(CURDATE())))

