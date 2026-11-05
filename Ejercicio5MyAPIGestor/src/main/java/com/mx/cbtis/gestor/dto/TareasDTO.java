package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Tareas;

/**
 * Datos que el cliente envia al API. Se usa en lugar de la entidad JPA para no
 * exponer el modelo de persistencia como cuerpo de las peticiones.
 */
public record TareasDTO(Integer id_tarea, String nombre, String descripcion, String fecha_vencimiento, String lista, String estado, Integer id_proyecto) {

	public Tareas toEntity() {
		return new Tareas(id_tarea, nombre, descripcion, fecha_vencimiento, lista, estado, id_proyecto);
	}
}
