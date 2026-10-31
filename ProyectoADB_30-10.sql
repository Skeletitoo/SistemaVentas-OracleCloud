-- Desactivar la lectura de '&' como variable de sustitución en SQL Developer
SET DEFINE OFF;

-- ====================================================
-- SISTEMA DE PUNTO DE VENTA Y CONTROL DE INVENTARIO
-- SCRIPT MAESTRO DE BASE DE DATOS ORACLE (DDL, DML, PL/SQL)
-- ====================================================

-- 1. ELIMINACIÓN DE TABLAS EXISTENTES
DROP TABLE detalle_compra CASCADE CONSTRAINTS;
DROP TABLE compra CASCADE CONSTRAINTS;
DROP TABLE detalle_venta CASCADE CONSTRAINTS;
DROP TABLE ventas CASCADE CONSTRAINTS;
DROP TABLE producto CASCADE CONSTRAINTS;
DROP TABLE proveedor CASCADE CONSTRAINTS;
DROP TABLE cliente CASCADE CONSTRAINTS;
DROP TABLE empleado CASCADE CONSTRAINTS;
DROP TABLE categoria CASCADE CONSTRAINTS;

-- 2. ESTRUCTURA DE TABLAS (DDL)
CREATE TABLE categoria(
    id_categoria NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(50) UNIQUE NOT NULL
);

CREATE TABLE empleado(
    id_empleado NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(150) NOT NULL,
    doc_identidad VARCHAR2(15) UNIQUE NOT NULL,
    rol VARCHAR2(20) CHECK (rol IN ('CAJERO', 'ADMINISTRADOR')) NOT NULL
);

CREATE TABLE cliente(
    id_cliente NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(150) NOT NULL,
    tipo_doc VARCHAR2(5) CHECK (tipo_doc IN ('DNI', 'RUC', 'PAS', 'CE')) NOT NULL,
    doc_identidad VARCHAR2(15) UNIQUE NOT NULL,
    sexo CHAR(1) CHECK(sexo IN ('H','M')),
    correo_elec VARCHAR2(60),
    telefono VARCHAR2(15)
);

CREATE TABLE proveedor(
    id_proveedor NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    razon_social VARCHAR2(150) NOT NULL,
    ruc VARCHAR2(11) UNIQUE NOT NULL,
    telefono VARCHAR2(15)
);

CREATE TABLE producto(
    id_producto NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_categoria NUMBER REFERENCES categoria(id_categoria) NOT NULL,
    nombre VARCHAR2(150) NOT NULL,
    precio_unitario NUMBER(10,2) NOT NULL,
    stock_actual NUMBER(5,0) CHECK (stock_actual >= 0) NOT NULL
);

CREATE TABLE ventas(
    id_venta NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo_venta VARCHAR2(10) CHECK (tipo_venta IN ('BOLETA', 'FACTURA')) NOT NULL,
    id_cliente NUMBER REFERENCES cliente(id_cliente) NOT NULL,
    id_empleado NUMBER REFERENCES empleado(id_empleado) NOT NULL,
    fecha_hora TIMESTAMP DEFAULT SYSDATE,
    monto_total NUMBER(10,2) DEFAULT 0
);

CREATE TABLE detalle_venta(
    id_detalle NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_venta NUMBER REFERENCES ventas(id_venta) NOT NULL,
    id_producto NUMBER REFERENCES producto(id_producto) NOT NULL,
    cantidad NUMBER(3) CHECK (cantidad > 0) NOT NULL,
    subtotal NUMBER(10,2) NOT NULL
);

CREATE TABLE compra(
    id_compra NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_proveedor NUMBER REFERENCES proveedor(id_proveedor) NOT NULL,
    fecha_hora TIMESTAMP DEFAULT SYSDATE,
    monto_total NUMBER(10,2) DEFAULT 0
);

CREATE TABLE detalle_compra(
    id_detalle NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_compra NUMBER REFERENCES compra(id_compra) NOT NULL,
    id_producto NUMBER REFERENCES producto(id_producto) NOT NULL,
    cantidad NUMBER(5,0) CHECK (cantidad > 0) NOT NULL,
    precio_compra NUMBER(10,2) NOT NULL,
    subtotal NUMBER(10,2) NOT NULL
);

-- 3. INSERCIÓN DE DATOS SEMILLA (CATÁLOGOS Y ENTIDADES)

-- Categorías
INSERT INTO categoria (nombre) VALUES ('Procesadores');
INSERT INTO categoria (nombre) VALUES ('Tarjetas Gráficas');
INSERT INTO categoria (nombre) VALUES ('Placas Madre');
INSERT INTO categoria (nombre) VALUES ('Memoria RAM');
INSERT INTO categoria (nombre) VALUES ('Almacenamiento');
INSERT INTO categoria (nombre) VALUES ('Periféricos');

-- Empleados
INSERT INTO empleado (nombre, doc_identidad, rol) VALUES ('Luis Rojas', '71234567', 'CAJERO');
INSERT INTO empleado (nombre, doc_identidad, rol) VALUES ('Marta Silva', '79876543', 'ADMINISTRADOR');
INSERT INTO empleado (nombre, doc_identidad, rol) VALUES ('Carlos Pinto', '74561238', 'CAJERO');

-- Clientes (14 Clientes completos)
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Juan Perez', 'DNI', '70011223', 'H', 'juan.p@email.com', '987654321');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Ana Torres', 'DNI', '71122334', 'M', 'ana.t@email.com', '912345678');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Inversiones Tech SAC', 'RUC', '20123456789', 'H', 'compras@itech.com', '014445555');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Miguel Soto', 'CE', '001122334', 'H', 'msoto@email.com', '999888777');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Carlos Mendoza', 'DNI', '72345678', 'H', 'c.mendoza@email.com', '981234567');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Comercial Lima EIRL', 'RUC', '20554433221', 'H', 'ventas@comerciallima.com', '014223344');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Lucía Ramos', 'DNI', '73456789', 'M', 'lucia.r@email.com', '972345678');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Sistemas & Software SAC', 'RUC', '20601122334', 'H', 'contacto@sistemas.pe', '015123456');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Diego Flores', 'DNI', '74567890', 'H', 'dflores@email.com', '963456789');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Valeria Castro', 'DNI', '75678901', 'M', 'vcastro@email.com', '954567890');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Esteban Morales', 'DNI', '76789012', 'H', 'emorales@email.com', '945678901');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Grupo Digital Peru SA', 'RUC', '20499887766', 'H', 'admin@grupodigital.pe', '014332211');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Patricia Vega', 'DNI', '77890123', 'M', 'pvega@email.com', '936789012');
INSERT INTO cliente (nombre, tipo_doc, doc_identidad, sexo, correo_elec, telefono) VALUES ('Gabriel Gutierrez', 'CE', '002233445', 'H', 'ggutierrez@email.com', '927890123');

-- Proveedores
INSERT INTO proveedor (razon_social, ruc, telefono) VALUES ('Grupo Deltron S.A. (Distribuidor Mayorista)', '20100123456', '015130000');
INSERT INTO proveedor (razon_social, ruc, telefono) VALUES ('Ingram Micro Perú S.A.', '20300987654', '016142000');
INSERT INTO proveedor (razon_social, ruc, telefono) VALUES ('Intel Perú S.A.', '20455667788', '012113300');
INSERT INTO proveedor (razon_social, ruc, telefono) VALUES ('AMD Del Perú S.A.C.', '20511223344', '014225500');
INSERT INTO proveedor (razon_social, ruc, telefono) VALUES ('ASUS Perú Technology', '20600334455', '013116600');
INSERT INTO proveedor (razon_social, ruc, telefono) VALUES ('Gigabyte Tech Perú', '20512345678', '015554433');
INSERT INTO proveedor (razon_social, ruc, telefono) VALUES ('Kingston Technology Corp', '20498765432', '014449988');

-- Catálogo de Productos (22 productos)
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (1, 'Intel Core i5-13400F', 950.00, 25);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (1, 'AMD Ryzen 5 5600X', 1054.90, 20);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (1, 'AMD Ryzen 7 5700X', 1150.00, 15);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (2, 'AMD Radeon RX 5500 XT', 850.00, 12);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (2, 'NVIDIA RTX 3060 12GB', 1350.00, 18);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (2, 'NVIDIA RTX 4060 8GB', 1450.50, 14);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (3, 'ASUS Prime B550M-A', 550.00, 22);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (3, 'Gigabyte B660M DS3H', 620.00, 19);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (3, 'MSI MAG B650 TOMAHAWK WIFI', 890.00, 15);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (3, 'MSI B550 GAMING PLUS', 590.00, 18);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (4, 'Kingston Fury Beast 16GB DDR4', 220.00, 45);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (4, 'Corsair Vengeance LPX 16GB DDR4', 210.00, 30);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (4, 'TeamGroup T-Force Delta RGB 32GB DDR5', 480.00, 25);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (4, 'Kingston Fury Renegade 32GB DDR5', 520.00, 20);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (5, 'SSD Western Digital Blue 1TB M.2', 310.00, 50);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (5, 'SSD Kingston NV2 1TB M.2 NVMe', 280.00, 40);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (5, 'SSD Samsung 980 PRO 1TB M.2', 450.00, 20);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (5, 'Disco Duro Seagate Barracuda 2TB', 240.00, 25);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (6, 'Mouse Logitech G203', 110.00, 35);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (6, 'Teclado Redragon Kumara', 160.00, 30);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (6, 'Audífonos HyperX Cloud II', 340.00, 20);
INSERT INTO producto (id_categoria, nombre, precio_unitario, stock_actual) VALUES (6, 'Monitor ASUS TUF Gaming 24" 165Hz', 690.00, 15);

-- 4. PROCEDIMIENTOS ALMACENADOS EN PL/SQL
CREATE OR REPLACE PROCEDURE SP_REGISTRAR_VENTA (
    p_id_cliente IN NUMBER,
    p_id_empleado IN NUMBER,
    p_monto_total IN NUMBER,
    p_id_venta OUT NUMBER
) AS
BEGIN
    INSERT INTO VENTAS (tipo_venta, id_cliente, id_empleado, monto_total)
    VALUES ('BOLETA', p_id_cliente, p_id_empleado, p_monto_total)
    RETURNING id_venta INTO p_id_venta;
END;
/

CREATE OR REPLACE PROCEDURE SP_REGISTRAR_DETALLE_VENTA (
    p_id_venta IN NUMBER,
    p_id_producto IN NUMBER,
    p_cantidad IN NUMBER,
    p_subtotal IN NUMBER
) AS
BEGIN
    INSERT INTO DETALLE_VENTA (id_venta, id_producto, cantidad, subtotal)
    VALUES (p_id_venta, p_id_producto, p_cantidad, p_subtotal);
    
    UPDATE PRODUCTO
    SET stock_actual = stock_actual - p_cantidad
    WHERE id_producto = p_id_producto;
END;
/

CREATE OR REPLACE PROCEDURE SP_REGISTRAR_COMPRA (
    p_id_proveedor IN NUMBER,
    p_monto_total IN NUMBER,
    p_id_compra OUT NUMBER
) AS
BEGIN
    INSERT INTO COMPRA (id_proveedor, monto_total)
    VALUES (p_id_proveedor, p_monto_total)
    RETURNING id_compra INTO p_id_compra;
END;
/

CREATE OR REPLACE PROCEDURE SP_REGISTRAR_DETALLE_COMPRA (
    p_id_compra IN NUMBER,
    p_id_producto IN NUMBER,
    p_cantidad IN NUMBER,
    p_precio_compra IN NUMBER,
    p_subtotal IN NUMBER
) AS
BEGIN
    INSERT INTO DETALLE_COMPRA (id_compra, id_producto, cantidad, precio_compra, subtotal)
    VALUES (p_id_compra, p_id_producto, p_cantidad, p_precio_compra, p_subtotal);
    
    UPDATE PRODUCTO
    SET stock_actual = stock_actual + p_cantidad
    WHERE id_producto = p_id_producto;
END;
/

-- 5. HISTORIAL DE COMPRAS (12 COMPRAS A PROVEEDORES)
INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (4, SYSDATE - 45, 8500.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (1, 2, 10, 850.00, 8500.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (3, SYSDATE - 40, 7500.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (2, 1, 10, 750.00, 7500.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (7, SYSDATE - 35, 3200.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (3, 9, 20, 160.00, 3200.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (1, SYSDATE - 30, 3250.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (4, 10, 10, 220.00, 2200.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (4, 11, 15, 70.00, 1050.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (2, SYSDATE - 25, 8800.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (5, 6, 8, 1100.00, 8800.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (5, SYSDATE - 20, 3200.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (6, 7, 8, 400.00, 3200.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (6, SYSDATE - 15, 3600.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (7, 8, 8, 450.00, 3600.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (7, SYSDATE - 10, 3200.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (8, 9, 20, 160.00, 3200.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (5, SYSDATE - 8, 4700.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (9, 13, 4, 650.00, 2600.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (9, 14, 5, 420.00, 2100.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (7, SYSDATE - 6, 6200.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (10, 15, 10, 150.00, 1500.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (10, 16, 8, 350.00, 2800.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (10, 17, 5, 380.00, 1900.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (1, SYSDATE - 4, 7160.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (11, 18, 15, 200.00, 3000.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (11, 19, 8, 320.00, 2560.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (11, 20, 10, 160.00, 1600.00);

INSERT INTO compra (id_proveedor, fecha_hora, monto_total) VALUES (2, SYSDATE - 2, 5400.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (12, 21, 10, 240.00, 2400.00);
INSERT INTO detalle_compra (id_compra, id_producto, cantidad, precio_compra, subtotal) VALUES (12, 22, 6, 500.00, 3000.00);

-- 6. HISTORIAL DE 30 VENTAS VARIADAS
INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 1, 1, SYSDATE - 30, 2109.80);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (1, 2, 2, 2109.80);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('FACTURA', 3, 2, SYSDATE - 29, 2700.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (2, 5, 2, 2700.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 2, 1, SYSDATE - 28, 770.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (3, 7, 1, 550.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (3, 9, 1, 220.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('FACTURA', 3, 2, SYSDATE - 27, 9200.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (4, 3, 8, 9200.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 4, 1, SYSDATE - 26, 2070.50);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (5, 6, 1, 1450.50);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (5, 8, 1, 620.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 1, 3, SYSDATE - 25, 4900.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (6, 1, 4, 3800.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (6, 9, 5, 1100.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 2, 1, SYSDATE - 24, 2920.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (7, 5, 2, 2700.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (7, 11, 2, 220.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 5, 1, SYSDATE - 23, 1510.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (8, 1, 1, 950.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (8, 18, 2, 560.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('FACTURA', 6, 2, SYSDATE - 22, 2080.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (9, 10, 4, 1240.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (9, 15, 4, 840.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 7, 3, SYSDATE - 21, 1644.90);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (10, 2, 1, 1054.90);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (10, 14, 1, 590.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('FACTURA', 8, 2, SYSDATE - 20, 5010.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (11, 5, 3, 4050.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (11, 16, 2, 960.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 9, 1, SYSDATE - 19, 780.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (12, 9, 2, 440.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (12, 21, 1, 340.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 10, 3, SYSDATE - 18, 380.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (13, 12, 1, 160.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (13, 11, 2, 220.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 11, 1, SYSDATE - 17, 2350.50);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (14, 6, 1, 1450.50);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (14, 8, 1, 620.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (14, 18, 1, 280.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('FACTURA', 12, 2, SYSDATE - 16, 2510.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (15, 10, 5, 1550.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (15, 20, 4, 960.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 13, 3, SYSDATE - 15, 2290.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (16, 4, 2, 1700.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (16, 14, 1, 590.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 14, 1, SYSDATE - 14, 2060.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (17, 21, 2, 680.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (17, 22, 2, 1380.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 1, 3, SYSDATE - 13, 2130.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (18, 1, 1, 950.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (18, 7, 1, 550.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (18, 15, 3, 630.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 2, 1, SYSDATE - 12, 3190.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (19, 3, 2, 2300.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (19, 13, 1, 890.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('FACTURA', 3, 2, SYSDATE - 11, 7362.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (20, 6, 4, 5802.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (20, 17, 3, 1560.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 4, 3, SYSDATE - 10, 1954.90);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (21, 2, 1, 1054.90);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (21, 19, 2, 900.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 5, 1, SYSDATE - 9, 1170.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (22, 18, 3, 840.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (22, 11, 3, 330.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('FACTURA', 6, 2, SYSDATE - 8, 3220.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (23, 13, 2, 1780.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (23, 16, 3, 1440.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 7, 3, SYSDATE - 7, 2060.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (24, 22, 2, 1380.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (24, 21, 2, 680.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('FACTURA', 8, 2, SYSDATE - 6, 2390.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (25, 19, 3, 1350.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (25, 17, 2, 1040.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 9, 1, SYSDATE - 5, 880.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (26, 11, 2, 220.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (26, 12, 2, 320.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (26, 21, 1, 340.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 10, 3, SYSDATE - 4, 3080.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (27, 1, 2, 1900.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (27, 14, 2, 1180.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('FACTURA', 12, 2, SYSDATE - 3, 8970.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (28, 3, 6, 6900.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (28, 22, 3, 2070.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 13, 1, SYSDATE - 2, 3300.50);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (29, 6, 1, 1450.50);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (29, 13, 1, 890.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (29, 16, 2, 960.00);

INSERT INTO ventas (tipo_venta, id_cliente, id_empleado, fecha_hora, monto_total) VALUES ('BOLETA', 14, 3, SYSDATE - 1, 2280.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (30, 19, 2, 900.00);
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (30, 22, 2, 1380.00);

COMMIT;