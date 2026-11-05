package com.mx.cbtis.gestor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import com.mx.cbtis.gestor.util.Validaciones;

class ValidacionesTest {

	@Test
	void textoValido() {
		assertTrue(Validaciones.texto("abc"));
	}

	@Test
	void textoInvalido() {
		assertFalse(Validaciones.texto(null));
		assertFalse(Validaciones.texto(""));
		assertFalse(Validaciones.texto("   "));
	}

	@Test
	void noSePuedeInstanciar() throws Exception {
		Constructor<Validaciones> c = Validaciones.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(c.getModifiers()));
		c.setAccessible(true);
		assertNotNull(c.newInstance());
		assertEquals(1, Validaciones.class.getDeclaredConstructors().length);
	}
}
