-- Esquema de la base de datos dbgestor (Gestor de proyectos)
-- Generado desde MariaDB. Solo estructura: no incluye datos ni credenciales.

CREATE DATABASE IF NOT EXISTS dbgestor CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE dbgestor;

DROP TABLE IF EXISTS `asignaciones`;
CREATE TABLE `asignaciones` (
  `id_asignacion` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `id_tarea` int(11) DEFAULT NULL,
  `fecha_asignacion` date DEFAULT NULL,
  PRIMARY KEY (`id_asignacion`),
  KEY `asignaciones_usuario_FK` (`id_usuario`),
  KEY `asignaciones_tareas_FK` (`id_tarea`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

DROP TABLE IF EXISTS `comentarios`;
CREATE TABLE `comentarios` (
  `id_comentario` int(11) NOT NULL AUTO_INCREMENT,
  `texto_comentario` varchar(250) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT NULL,
  `id_usuario` varchar(100) DEFAULT NULL,
  `id_tarea` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_comentario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

DROP TABLE IF EXISTS `proyectos`;
CREATE TABLE `proyectos` (
  `id_proyecto` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_finalizacion` date DEFAULT NULL,
  `estado_proyecto` varchar(100) DEFAULT NULL,
  `fondo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_proyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

DROP TABLE IF EXISTS `tareas`;
CREATE TABLE `tareas` (
  `id_tarea` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `lista` varchar(255) DEFAULT NULL,
  `id_proyecto` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_tarea`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `correo_electronico` varchar(100) DEFAULT NULL,
  `contrasena` varchar(100) DEFAULT NULL,
  `rol` varchar(100) DEFAULT NULL,
  `imagen_usuario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

