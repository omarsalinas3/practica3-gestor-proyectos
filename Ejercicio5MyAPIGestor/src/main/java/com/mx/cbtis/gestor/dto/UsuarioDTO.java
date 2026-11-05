package com.mx.cbtis.gestor.dto;

import com.mx.cbtis.gestor.modelo.Usuario;

/**
 * Datos que el cliente envia al API. Se usa en lugar de la entidad JPA para no
 * exponer el modelo de persistencia como cuerpo de las peticiones.
 */
public record UsuarioDTO(Integer id_usuario, String nombre, String correo_electronico, String contrasena, String rol, String imagen_usuario) {

	public Usuario toEntity() {
		return new Usuario(id_usuario, nombre, correo_electronico, contrasena, rol, imagen_usuario);
	}
}
