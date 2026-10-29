package com.mx.cbtis.gestor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

class Ejercicio5MyApiGestorApplicationMainTest {

	@Test
	void mainArrancaLaAplicacion() {
		try (MockedStatic<SpringApplication> spring = mockStatic(SpringApplication.class)) {
			Ejercicio5MyApiGestorApplication.main(new String[] {});
			spring.verify(() -> SpringApplication.run(eq(Ejercicio5MyApiGestorApplication.class), any(String[].class)), times(1));
		}
	}

	@Test
	void seInstanciaLaClase() {
		assertNotNull(new Ejercicio5MyApiGestorApplication());
	}
}
