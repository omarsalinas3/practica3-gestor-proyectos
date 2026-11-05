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
class AsignacionesControllerTest {

	@Mock AsignacionesRepository repo;
	@InjectMocks AsignacionesController controller;

	private AsignacionesDTO valida() {
		return new AsignacionesDTO(1, 2, 3, "2026-10-12");
	}

	@Test void buscarExistenteEInexistente() {
		when(repo.findById(1)).thenReturn(Optional.of(valida().toEntity()));
		when(repo.findById(2)).thenReturn(Optional.empty());
		assertEquals(2, controller.getAsignacion(1).getId_usuario());
		assertNull(controller.getAsignacion(2).getId_asignacion());
	}

	@Test void addValidoEInvalido() {
		assertEquals(1, controller.addAsignacion(valida()));
		assertEquals(0, controller.addAsignacion(new AsignacionesDTO(null, null, null, null)));
		assertEquals(0, controller.addAsignacion(null));
		assertEquals(0, controller.addAsignacion(new AsignacionesDTO(null, 1, null, null)));
		assertEquals(0, controller.addAsignacion(new AsignacionesDTO(null, null, 1, null)));
		verify(repo, times(1)).save(any(Asignaciones.class));
	}

	@Test void updateValidoEInvalido() {
		assertEquals(1, controller.updateAsignacion(valida()));
		assertEquals(0, controller.updateAsignacion(new AsignacionesDTO(null, null, null, null)));
		verify(repo, times(1)).save(any(Asignaciones.class));
	}

	@Test void deleteValidoYNulo() {
		assertEquals(1, controller.deleteAsignacion(valida()));
		assertEquals(0, controller.deleteAsignacion(null));
		verify(repo, times(1)).delete(any(Asignaciones.class));
	}

	@Test void listar() {
		when(repo.findAll()).thenReturn(Arrays.asList(valida().toEntity()));
		assertEquals(1, controller.getAsignaciones().size());
	}
}
