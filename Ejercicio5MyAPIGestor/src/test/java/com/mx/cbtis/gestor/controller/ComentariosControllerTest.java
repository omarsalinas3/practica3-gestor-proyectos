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
class ComentariosControllerTest {

	@Mock ComentariosRepository repo;
	@InjectMocks ComentariosController controller;

	private Comentarios valido() {
		return new Comentarios(1, "5", 3, "Texto", "2026-10-12 18:30");
	}

	@Test void comentariosPorTarea() {
		when(repo.findByTarea(3)).thenReturn(Arrays.asList(valido()));
		Comentarios filtro = new Comentarios();
		filtro.setId_tarea(3);
		assertEquals(1, controller.getComentario(filtro).size());
	}

	@Test void buscarExistenteEInexistente() {
		when(repo.findById(1)).thenReturn(Optional.of(valido()));
		when(repo.findById(2)).thenReturn(Optional.empty());
		assertEquals("Texto", controller.getComentario(1).getTexto_comentario());
		assertNull(controller.getComentario(2).getId_comentario());
	}

	@Test void addValidoEInvalido() {
		assertEquals(1, controller.addComentario(valido()));
		assertEquals(0, controller.addComentario(new Comentarios()));
		assertEquals(0, controller.addComentario(null));
		assertEquals(0, controller.addComentario(new Comentarios(null, "", 1, "t", null)));
		assertEquals(0, controller.addComentario(new Comentarios(null, "5", null, "t", null)));
		assertEquals(0, controller.addComentario(new Comentarios(null, "5", 1, "  ", null)));
		verify(repo, times(1)).save(any(Comentarios.class));
	}

	@Test void updateValidoEInvalido() {
		assertEquals(1, controller.updateComentario(valido()));
		assertEquals(0, controller.updateComentario(new Comentarios()));
		verify(repo, times(1)).save(any(Comentarios.class));
	}

	@Test void deleteValido() {
		assertEquals(1, controller.deleteComentario(valido()));
		verify(repo).delete(any(Comentarios.class));
	}

	@Test void listar() {
		when(repo.findAll()).thenReturn(Arrays.asList(valido(), valido()));
		assertEquals(2, controller.getComentarios().size());
	}
}
