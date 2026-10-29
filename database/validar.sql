-- Validacion del esquema dbgestor sobre un MariaDB temporal.
-- Falla (SIGNAL) si falta alguna tabla o si el CRUD basico no funciona.
USE dbgestor;
DELIMITER //
CREATE PROCEDURE validar_esquema()
BEGIN
  DECLARE n INT;
  SELECT COUNT(*) INTO n FROM information_schema.tables
   WHERE table_schema = 'dbgestor'
     AND table_name IN ('usuario','proyectos','tareas','asignaciones','comentarios','hibernate_sequence');
  IF n <> 6 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Faltan tablas en el esquema';
  END IF;

  SELECT COUNT(*) INTO n FROM information_schema.table_constraints
   WHERE table_schema = 'dbgestor' AND constraint_type = 'PRIMARY KEY'
     AND table_name IN ('usuario','proyectos','tareas','asignaciones','comentarios');
  IF n <> 5 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Falta una llave primaria';
  END IF;

  INSERT INTO usuario (nombre, correo_electronico, contrasena, rol, imagen_usuario)
  VALUES ('Prueba CI', 'ci@prueba.com', 'x', 'Estudiante', '#000000');
  SELECT COUNT(*) INTO n FROM usuario WHERE correo_electronico = 'ci@prueba.com';
  IF n <> 1 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fallo el INSERT/SELECT de usuario';
  END IF;
  DELETE FROM usuario WHERE correo_electronico = 'ci@prueba.com';
  SELECT COUNT(*) INTO n FROM usuario WHERE correo_electronico = 'ci@prueba.com';
  IF n <> 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Fallo el DELETE de usuario';
  END IF;
  SELECT 'ESQUEMA VALIDO: 6 tablas, 5 llaves primarias y CRUD correcto' AS resultado;
END//
DELIMITER ;
CALL validar_esquema();
DROP PROCEDURE validar_esquema;
