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

CREATE TABLE pdfs(
	idPdf INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(250)
);

CREATE TABLE solicitudes(
	idSolicitud INT PRIMARY KEY AUTO_INCREMENT,
    tipoSolicitud VARCHAR(20),
    fechaRegistro TIMESTAMP DEFAULT current_timestamp,
    estado VARCHAR(20),
    descripcion TEXT,
    idPdf INT,
    FOREIGN KEY (idPdf) REFERENCES pdfs(idPdf) 
);

CREATE TABLE usuarios(
	idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    contrasena VARCHAR(250),
    celular INT,
    correo VARCHAR(200),
    idRol INT,
    FOREIGN KEY (idRol) REFERENCES roles(idRol) 
);

CREATE TABLE solicitudesUsuarios(
	idSolicitudUsuario INT PRIMARY KEY AUTO_INCREMENT,
    idUsuario INT,
    idSolicitud INT,
	FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario),
	FOREIGN KEY (idSolicitud) REFERENCES solicitudes(idSolicitud) 
);