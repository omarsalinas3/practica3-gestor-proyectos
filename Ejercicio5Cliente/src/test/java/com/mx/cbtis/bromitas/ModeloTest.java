package com.mx.cbtis.bromitas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.mx.cbtis.modelo.Asignaciones;
import com.mx.cbtis.modelo.Comentarios;
import com.mx.cbtis.modelo.Proyectos;
import com.mx.cbtis.modelo.Tareas;
import com.mx.cbtis.modelo.Usuario;

/**
 * Pruebas de las clases del modelo: constructores, getters y setters.
 */
class ModeloTest {

	static Stream<Class<?>> modelos() {
		return Stream.of(Usuario.class, Proyectos.class, Tareas.class, Asignaciones.class, Comentarios.class);
	}

	private static Object ejemplo(Class<?> tipo, int n) {
		if (tipo == Integer.class || tipo == int.class) {
			return n;
		}
		return "valor" + n;
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("modelos")
	@DisplayName("Los atributos se inician en null con el constructor vacio")
	void constructorVacioDejaAtributosEnNull(Class<?> clase) throws Exception {
		Object objeto = clase.getDeclaredConstructor().newInstance();
		for (Method getter : clase.getMethods()) {
			if (getter.getName().startsWith("get") && getter.getParameterCount() == 0 && getter.getDeclaringClass() == clase) {
				assertNull(getter.invoke(objeto), getter.getName());
			}
		}
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("modelos")
	@DisplayName("El constructor completo asigna todos los atributos")
	void constructorCompletoAsignaAtributos(Class<?> clase) throws Exception {
		Constructor<?> completo = Arrays.stream(clase.getConstructors())
				.max((a, b) -> Integer.compare(a.getParameterCount(), b.getParameterCount())).get();
		Class<?>[] tipos = completo.getParameterTypes();
		Object[] valores = new Object[tipos.length];
		for (int i = 0; i < tipos.length; i++) {
			valores[i] = ejemplo(tipos[i], i + 1);
		}
		Object objeto = completo.newInstance(valores);

		int i = 0;
		for (Method getter : clase.getDeclaredMethods()) {
			if (getter.getName().startsWith("get") && getter.getParameterCount() == 0) {
				assertNotNull(getter.invoke(objeto), getter.getName());
				i++;
			}
		}
		assertEquals(tipos.length, i, "cada argumento del constructor tiene su getter");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("modelos")
	@DisplayName("Cada setter guarda el valor que despues devuelve su getter")
	void settersYGetters(Class<?> clase) throws Exception {
		Object objeto = clase.getDeclaredConstructor().newInstance();
		int probados = 0;
		for (Method setter : clase.getDeclaredMethods()) {
			if (setter.getName().startsWith("set") && setter.getParameterCount() == 1) {
				Object valor = ejemplo(setter.getParameterTypes()[0], 42);
				setter.invoke(objeto, valor);
				Method getter = clase.getMethod("get" + setter.getName().substring(3));
				assertEquals(valor, getter.invoke(objeto), setter.getName());
				probados++;
			}
		}
		assertEquals(true, probados > 0);
	}
}
