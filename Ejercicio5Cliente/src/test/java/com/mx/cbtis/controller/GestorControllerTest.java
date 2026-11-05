package com.mx.cbtis.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

/**
 * Pruebas automaticas del servicio de consulta del frontend (GestorController).
 * El backend se simula con MockRestServiceServer, por lo que no requiere la API real.
 */
class GestorControllerTest {

	private MockRestServiceServer backend;
	private MockMvc mvc;

	@BeforeEach
	void setUp() {
		RestTemplate template = new RestTemplate();
		backend = MockRestServiceServer.createServer(template);
		GestorController controller = new GestorController(template);
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	@DisplayName("AUT-E01 Servicio de consulta: el backend devuelve informacion valida")
	void AUT_E01_consultarProyectosConRespuestaValida() throws Exception {
		backend.expect(requestTo("http://localhost:8081/buscarProyectos"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess("[{\"id_proyecto\":1,\"nombre\":\"Proyecto A\",\"estado_proyecto\":\"En proceso\"},"
						+ "{\"id_proyecto\":2,\"nombre\":\"Proyecto B\",\"estado_proyecto\":\"Finalizado\"}]",
						MediaType.APPLICATION_JSON));

		mvc.perform(post("/consultarProyectos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].nombre").value("Proyecto A"))
				.andExpect(jsonPath("$[1].estado_proyecto").value("Finalizado"));
	}

	@Test
	@DisplayName("AUT-F01 Servicio de consulta: el backend devuelve un error")
	void AUT_F01_consultarProyectosConErrorDelBackend() throws Exception {
		backend.expect(requestTo("http://localhost:8081/buscarProyectos"))
				.andRespond(withServerError());

		mvc.perform(post("/consultarProyectos"))
				.andExpect(status().isServiceUnavailable())
				.andExpect(jsonPath("$.error").exists());
	}
}
