package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.dto.*;
import com.mx.cbtis.gestor.modelo.*;
import com.mx.cbtis.gestor.repository.*;

@ExtendWith(MockitoExtension.class)
class ProyectosControllerTest {

	@Mock ProtectosRepository repo;
	@InjectMocks ProyectosController controller;

	private ProyectosDTO valido() {
		return new ProyectosDTO(1, "Proy", "desc", "2026-01-01", "2026-02-01", "En proceso", "/Images/blanco.png");
	}

	@Test void buscarExistente() {
		when(repo.findById(1)).thenReturn(Optional.of(valido().toEntity()));
		assertEquals("Proy", controller.getProyecto(1).getNombre());
	}

	@Test void buscarInexistente() {
		when(repo.findById(5)).thenReturn(Optional.empty());
		assertNull(controller.getProyecto(5).getId_proyecto());
	}

	@Test void addValidoEInvalido() {
		assertEquals(1, controller.addProyecto(valido()));
		assertEquals(0, controller.addProyecto(new ProyectosDTO(null, null, null, null, null, null, null)));
		assertEquals(0, controller.addProyecto(null));
		assertEquals(0, controller.addProyecto(new ProyectosDTO(null, " ", null, null, null, null, null)));
		verify(repo, times(1)).save(any(Proyectos.class));
	}

	@Test void updateValidoEInvalido() {
		assertEquals(1, controller.updateProyecto(valido()));
		assertEquals(0, controller.updateProyecto(new ProyectosDTO(null, null, null, null, null, null, null)));
		verify(repo, times(1)).save(any(Proyectos.class));
	}

	@Test void deleteValidoYNulo() {
		assertEquals(1, controller.deleteProyecto(valido()));
		assertEquals(0, controller.deleteProyecto(null));
		verify(repo, times(1)).delete(any(Proyectos.class));
	}

	@Test void listar() {
		when(repo.findAll()).thenReturn(Arrays.asList(valido().toEntity()));
		assertEquals(1, controller.getProyectos().size());
	}
}
