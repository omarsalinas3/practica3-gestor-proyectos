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
class UsuarioControllerTest {

	@Mock UsuarioRepository repo;
	@InjectMocks UsuarioController controller;

	private Usuario valido() {
		return new Usuario(1, "Ana", "ana@mail.com", "1234", "Estudiante", "#FFFFFF");
	}

	@Test @DisplayName("Login correcto devuelve el usuario")
	void loginCorrecto() {
		when(repo.findByCorreoPass("ana@mail.com", "1234")).thenReturn(Optional.of(valido()));
		Usuario r = controller.getUsuarioLogin(new Usuario(null, null, "ana@mail.com", "1234", null, null));
		assertEquals("Ana", r.getNombre());
	}

	@Test @DisplayName("Login incorrecto devuelve nombre 000")
	void loginIncorrecto() {
		when(repo.findByCorreoPass(any(), any())).thenReturn(Optional.empty());
		assertEquals("000", controller.getUsuarioLogin(new Usuario()).getNombre());
	}

	@Test void buscarExistente() {
		when(repo.findById(1)).thenReturn(Optional.of(valido()));
		assertEquals("Ana", controller.getUsuario(1).getNombre());
	}

	@Test void buscarInexistenteDevuelveVacio() {
		when(repo.findById(9)).thenReturn(Optional.empty());
		assertNull(controller.getUsuario(9).getId_usuario());
	}

	@Test void addValido() {
		assertEquals(1, controller.addUsuario(valido()));
		verify(repo).save(any(Usuario.class));
	}

	@Test void addInvalidoNoGuarda() {
		assertEquals(0, controller.addUsuario(new Usuario()));
		assertEquals(0, controller.addUsuario(null));
		assertEquals(0, controller.addUsuario(new Usuario(null, "  ", "a@a.com", "x", null, null)));
		assertEquals(0, controller.addUsuario(new Usuario(null, "Ana", null, "x", null, null)));
		assertEquals(0, controller.addUsuario(new Usuario(null, "Ana", "a@a.com", "", null, null)));
		verify(repo, never()).save(any());
	}

	@Test void updateValidoEInvalido() {
		assertEquals(1, controller.updateUsuario(valido()));
		assertEquals(0, controller.updateUsuario(new Usuario()));
		verify(repo, times(1)).save(any(Usuario.class));
	}

	@Test void deleteValido() {
		assertEquals(1, controller.deleteUsuario(valido()));
		verify(repo).delete(any(Usuario.class));
	}

	@Test void listar() {
		when(repo.findAll()).thenReturn(Arrays.asList(valido(), valido()));
		assertEquals(2, controller.getUsuarios().size());
	}
}
