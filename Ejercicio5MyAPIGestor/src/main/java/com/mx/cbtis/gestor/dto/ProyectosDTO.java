package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Proyectos;

/**
 * Datos que el cliente envia al API. Se usa en lugar de la entidad JPA para no
 * exponer el modelo de persistencia como cuerpo de las peticiones.
 */
public record ProyectosDTO(Integer id_proyecto, String nombre, String descripcion, String fecha_inicio, String fecha_finalizacion, String estado_proyecto, String fondo) {

	public Proyectos toEntity() {
		return new Proyectos(id_proyecto, nombre, descripcion, fecha_inicio, fecha_finalizacion, estado_proyecto, fondo);
	}
}
