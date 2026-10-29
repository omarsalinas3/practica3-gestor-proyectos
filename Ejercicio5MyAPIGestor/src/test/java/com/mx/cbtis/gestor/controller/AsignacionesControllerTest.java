package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mx.cbtis.gestor.modelo.*;
import com.mx.cbtis.gestor.repository.*;

@ExtendWith(MockitoExtension.class)
class AsignacionesControllerTest {

	@Mock AsignacionesRepository repo;
	@InjectMocks AsignacionesController controller;

	private Asignaciones valida() {
		return new Asignaciones(1, 2, 3, "2026-10-12");
	}

	@Test void buscarExistenteEInexistente() {
		when(repo.findById(1)).thenReturn(Optional.of(valida()));
		when(repo.findById(2)).thenReturn(Optional.empty());
		assertEquals(2, controller.getAsignacion(1).getId_usuario());
		assertNull(controller.getAsignacion(2).getId_asignacion());
	}

	@Test void addValidoEInvalido() {
		assertEquals(1, controller.addAsignacion(valida()));
		assertEquals(0, controller.addAsignacion(new Asignaciones()));
		assertEquals(0, controller.addAsignacion(null));
		assertEquals(0, controller.addAsignacion(new Asignaciones(null, 1, null, null)));
		assertEquals(0, controller.addAsignacion(new Asignaciones(null, null, 1, null)));
		verify(repo, times(1)).save(any(Asignaciones.class));
	}

	@Test void updateValidoEInvalido() {
		assertEquals(1, controller.updateAsignacion(valida()));
		assertEquals(0, controller.updateAsignacion(new Asignaciones()));
		verify(repo, times(1)).save(any(Asignaciones.class));
	}

	@Test void deleteValido() {
		assertEquals(1, controller.deleteAsignacion(valida()));
		verify(repo).delete(any(Asignaciones.class));
	}

	@Test void listar() {
		when(repo.findAll()).thenReturn(Arrays.asList(valida()));
		assertEquals(1, controller.getAsignaciones().size());
	}
}
