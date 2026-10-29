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
class TareasControllerTest {

	@Mock TareasRepository repo;
	@InjectMocks TareasController controller;

	private Tareas valida() {
		return new Tareas(1, "Tarea", "desc", "2026-03-01", "pendientes", "danger", 7);
	}

	@Test void tareasPorProyecto() {
		when(repo.findByIdProyecto(7)).thenReturn(Arrays.asList(valida()));
		Tareas filtro = new Tareas();
		filtro.setId_proyecto(7);
		List<Tareas> r = controller.getTarea(filtro);
		assertEquals(1, r.size());
	}

	@Test void buscarExistenteEInexistente() {
		when(repo.findById(1)).thenReturn(Optional.of(valida()));
		when(repo.findById(2)).thenReturn(Optional.empty());
		assertEquals("Tarea", controller.getTarea(1).getNombre());
		assertNull(controller.getTarea(2).getId_tarea());
	}

	@Test void addValidoEInvalido() {
		assertEquals(1, controller.addTareas(valida()));
		assertEquals(0, controller.addTareas(new Tareas()));
		assertEquals(0, controller.addTareas(null));
		assertEquals(0, controller.addTareas(new Tareas(null, "x", null, null, null, null, null)));
		assertEquals(0, controller.addTareas(new Tareas(null, " ", null, null, null, null, 3)));
		verify(repo, times(1)).save(any(Tareas.class));
	}

	@Test void updateValidoEInvalido() {
		assertEquals(1, controller.updateTareas(valida()));
		assertEquals(0, controller.updateTareas(new Tareas()));
		verify(repo, times(1)).save(any(Tareas.class));
	}

	@Test void deleteValido() {
		assertEquals(1, controller.deleteTareas(valida()));
		verify(repo).delete(any(Tareas.class));
	}

	@Test void listar() {
		when(repo.findAll()).thenReturn(Arrays.asList(valida(), valida()));
		assertEquals(2, controller.getTareas().size());
	}
}
