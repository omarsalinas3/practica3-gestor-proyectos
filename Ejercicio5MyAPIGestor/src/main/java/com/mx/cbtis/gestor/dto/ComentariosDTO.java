package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Comentarios;

/**
 * Datos que el cliente envia al API. Se usa en lugar de la entidad JPA para no
 * exponer el modelo de persistencia como cuerpo de las peticiones.
 */
public record ComentariosDTO(Integer id_comentario, String id_usuario, Integer id_tarea, String texto_comentario, String fecha_hora) {

	public Comentarios toEntity() {
		return new Comentarios(id_comentario, id_usuario, id_tarea, texto_comentario, fecha_hora);
	}
}
