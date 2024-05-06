-- Verifica si la base de datos "Gestor_tutoriales" existe
SELECT IF(EXISTS (SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'pqrs'), 'exists', 'not_exists') AS db_status;

-- Si la base de datos no existe, créala
CREATE DATABASE IF NOT EXISTS pqrs;

-- Selecciona la base de datos recién creada para realizar operaciones en ella
USE pqrs;

-- Crea la tabla 'roles'
CREATE TABLE roles(
    idRol INT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único autoincremental
    rol VARCHAR(20)
);

   -- Roles definidos anteriormente
    INSERT INTO roles(rol) VALUES('usuario'),
    ('administrador');

CREATE TABLE pdfs(
	idPdf INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(250)
);

CREATE TABLE usuarios(
	idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    cedula VARCHAR(20),
    contrasena VARCHAR(250),
    celular VARCHAR(20),
    correo VARCHAR(200),
    idRol INT,
    FOREIGN KEY (idRol) REFERENCES roles(idRol) 
);

CREATE TABLE solicitudes(
	idSolicitud INT PRIMARY KEY AUTO_INCREMENT,
    nombreSolicitud VARCHAR(100),
    tipoSolicitud VARCHAR(20),
    fechaRegistro TIMESTAMP DEFAULT current_timestamp,
    estado VARCHAR(20),
    descripcion TEXT,
    idPdf INT DEFAULT NULL,
    idUsuario INT,
    FOREIGN KEY (idPdf) REFERENCES pdfs(idPdf),
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario)
);


# -------------------------------------------------------------
# Procedimientos almacenados para la funcionalidad del proyecto
# -------------------------------------------------------------

DELIMITER //

CREATE PROCEDURE AgregarUsuario(
    IN p_nombre VARCHAR(100),
    IN p_apellido VARCHAR(100),
    IN p_cedula VARCHAR(20),
    IN p_contrasena VARCHAR(250),
    IN p_celular VARCHAR(20),
    IN p_correo VARCHAR(200),
    IN p_idRol INT
)
BEGIN
    INSERT INTO usuarios(nombre, apellido, cedula, contrasena, celular, correo, idRol)
    VALUES(p_nombre, p_apellido, p_cedula, p_contrasena, p_celular, p_correo, p_idRol);
END //

DELIMITER ;
 INSERT INTO usuarios(nombre, apellido, cedula, contrasena, celular, correo, idRol)
 VALUES('Maria', 'Casanova', '2222', '2222', '2222', 'Casanova', '2');
INSERT INTO usuarios(nombre, apellido, cedula, contrasena, celular, correo, idRol)
 VALUES('Maria', 'Casanova', '3333', '3333', '2222', 'Casanova', '1');
DELIMITER //

CREATE PROCEDURE AgregarPDF(
    IN p_nombre VARCHAR(250)
)
BEGIN
    INSERT INTO pdfs(nombre)
    VALUES (p_nombre);
END //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE AgregarSolicitud(
    IN p_nombreSolicitud VARCHAR(100),
    IN p_tipoSolicitud VARCHAR(20),
    IN p_estado VARCHAR(20),
    IN p_descripcion TEXT,
    IN p_idPdf INT,
    IN p_idUsuario INT
)
BEGIN
    INSERT INTO solicitudes(nombreSolicitud, tipoSolicitud, estado, descripcion, idPdf, idUsuario)
    VALUES(p_nombreSolicitud, p_tipoSolicitud, p_estado, p_descripcion, p_idPdf, p_idUsuario);
END //

DELIMITER ;
