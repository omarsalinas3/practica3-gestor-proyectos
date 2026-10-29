package com.mx.cbtis.gestor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Prueba de integracion que levanta el contexto completo y necesita MariaDB.
 * Solo se ejecuta cuando RUN_DB_TESTS=true; las pruebas unitarias (Mockito)
 * no dependen de la base de datos y son las que corren en Jenkins.
 */
@SpringBootTest
@EnabledIfEnvironmentVariable(named = "RUN_DB_TESTS", matches = "true")
class Ejercicio5MyApiGestorApplicationTests {

	@Test
	void contextLoads() {
	}

}
