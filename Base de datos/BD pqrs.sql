-- Verifica si la base de datos "Gestor_tutoriales" existe
SELECT IF(EXISTS (SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'pqrs'), 'exists', 'not_exists') AS db_status;

-- Si la base de datos no existe, créala
CREATE DATABASE IF NOT EXISTS pqrs CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

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
    pdf TEXT DEFAULT NULL,
    idUsuario INT,
	respuesta TEXT DEFAULT NULL,
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
 INSERT INTO usuarios(nombre, apellido, cedula, contrasena, celular, correo, idRol)
 VALUES('Johan', 'Ordoñez', '0000', '0000', '0000', 'johanrealpelibro@gmail.com', '2');
DELIMITER //



DELIMITER ;


DELIMITER //

CREATE PROCEDURE AgregarSolicitud(
    IN p_nombreSolicitud VARCHAR(100),
    IN p_tipoSolicitud VARCHAR(20),
    IN p_estado VARCHAR(20),
    IN p_descripcion TEXT,
    IN p_pdf TEXT,
    IN p_idUsuario INT,
    IN p_respuesta TEXT
)
BEGIN
    INSERT INTO solicitudes(nombreSolicitud, tipoSolicitud, estado, descripcion, pdf, idUsuario, respuesta)
    VALUES(p_nombreSolicitud, p_tipoSolicitud, p_estado, p_descripcion, p_pdf, p_idUsuario, p_respuesta);
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE editarRespuesta(
    IN p_idSolicitud INT,
    IN p_respuesta TEXT,
    IN p_estado VARCHAR(20)
)
BEGIN
    UPDATE solicitudes
    SET respuesta = p_respuesta,
        estado = p_estado
    WHERE idSolicitud = p_idSolicitud;
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE EditarSolicitud(
    IN p_idSolicitud INT,
    IN p_nombreSolicitud VARCHAR(100),
    IN p_tipoSolicitud VARCHAR(20),
    IN p_estado VARCHAR(20),
    IN p_descripcion TEXT,
    IN p_pdf TEXT,
    IN p_idUsuario INT,
    IN p_respuesta TEXT
)
BEGIN
    UPDATE solicitudes
    SET 
        nombreSolicitud = p_nombreSolicitud,
        tipoSolicitud = p_tipoSolicitud,
        estado = p_estado,
        descripcion = p_descripcion,
        pdf = p_pdf,
        idUsuario = p_idUsuario,
        respuesta = p_respuesta
    WHERE idSolicitud = p_idSolicitud;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE editarUsuario(
    IN p_idUsuario INT,
    IN p_nombre VARCHAR(100),
    IN p_apellido VARCHAR(100),
    IN p_cedula VARCHAR(20),
    IN p_celular VARCHAR(20),
    IN p_correo VARCHAR(200)
)
BEGIN
    UPDATE usuarios
    SET nombre = p_nombre,
        apellido = p_apellido,
        cedula = p_cedula,
        celular = p_celular,
        correo = p_correo
    WHERE idUsuario = p_idUsuario;
END //

DELIMITER ;

