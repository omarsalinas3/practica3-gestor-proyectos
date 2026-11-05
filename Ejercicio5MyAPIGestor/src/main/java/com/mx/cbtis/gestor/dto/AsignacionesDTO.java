package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Asignaciones;

/**
 * Datos que el cliente envia al API. Se usa en lugar de la entidad JPA para no
 * exponer el modelo de persistencia como cuerpo de las peticiones.
 */
public record AsignacionesDTO(Integer id_asignacion, Integer id_usuario, Integer id_tarea, String fecha_asignacion) {

	public Asignaciones toEntity() {
		return new Asignaciones(id_asignacion, id_usuario, id_tarea, fecha_asignacion);
	}
}
