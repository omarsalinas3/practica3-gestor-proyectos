-- Dato inicial que necesita el backend para generar los identificadores.
-- Hibernate toma los ids de la tabla hibernate_sequence y falla si esta vacia
-- ("could not read a hi value"). Docker Compose carga este archivo despues de schema.sql.
USE dbgestor;
INSERT INTO hibernate_sequence (next_val) VALUES (1);
