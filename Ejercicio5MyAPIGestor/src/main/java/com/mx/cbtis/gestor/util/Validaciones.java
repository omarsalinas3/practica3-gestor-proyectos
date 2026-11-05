package com.mx.cbtis.gestor.util;

/**
 * Validaciones compartidas por los controladores.
 */
public final class Validaciones {

	private Validaciones() {
	}

	/** Indica si el texto no es nulo ni esta en blanco. */
	public static boolean texto(String valor) {
		return valor != null && !valor.trim().isEmpty();
	}
}
