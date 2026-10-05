CREATE DATABASE IF NOT EXISTS crediya_db;

USE crediya_db;

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    documento VARCHAR(30) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    correo VARCHAR(80) NOT NULL,
    salario DECIMAL(10,2) NOT NULL
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    documento VARCHAR(30) NOT NULL,
    correo VARCHAR(80) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

CREATE TABLE prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    empleado_id INT NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    interes DECIMAL(5,2) NOT NULL,
    cuotas INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,

    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (empleado_id) REFERENCES empleados(id)
);

CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prestamo_id INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (prestamo_id) REFERENCES prestamos(id)
);
USE crediya_db;


SELECT * FROM clientes;
ALTER TABLE prestamos
ADD COLUMN saldo_pendiente DECIMAL(12,2);
DESCRIBE prestamos;
SELECT * FROM prestamos;
SELECT id, monto, saldo_pendiente
FROM prestamos;
SELECT * FROM clientes;


UPDATE prestamos
SET saldo_pendiente = 500000
WHERE id = 2;
SELECT *
FROM prestamos
ORDER BY id DESC
LIMIT 1;
SELECT id, saldo_pendiente
FROM prestamos
WHERE id = 6;
SELECT * FROM empleados
ORDER BY id DESC
LIMIT 2;

SELECT * FROM clientes;
SELECT * FROM empleados;
SELECT * FROM prestamos;
SELECT * FROM pagos;
SELECT id, monto, saldo_pendiente
FROM prestamos
WHERE id = 8;