package com.mx.cbtis.gestor.controller;

import static com.mx.cbtis.gestor.util.Validaciones.texto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.cbtis.gestor.dto.UsuarioDTO;
import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

@RestController
public class UsuarioController {

	private final UsuarioRepository usuariotable;

	public UsuarioController(UsuarioRepository usuariotable) {
		this.usuariotable = usuariotable;
	}

	@PostMapping("/getUsuarioLogin")
	public Usuario getUsuarioLogin(@RequestBody UsuarioDTO credenciales) {
		Optional<Usuario> usuario = usuariotable.findByCorreoPass(credenciales.correo_electronico(), credenciales.contrasena());
		Usuario respuesta = new Usuario();
		if (usuario.isPresent()) {
			respuesta = usuario.get();
		} else {
			respuesta.setNombre("000");
		}
		return respuesta;
	}

	@GetMapping("/buscarUsuario")
	public Usuario getUsuario(@RequestParam int id) {
		Usuario buscar = new Usuario();
		Optional<Usuario> encontrado = usuariotable.findById(id);
		if (encontrado.isPresent()) {
			buscar = encontrado.get();
		}
		return buscar;
	}

	@PostMapping("/addUsuario")
	public int addUsuario(@RequestBody UsuarioDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			usuariotable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/updateUsuario")
	public int updateUsuario(@RequestBody UsuarioDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			usuariotable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@DeleteMapping("/deleteUsuario")
	public int deleteUsuario(@RequestBody UsuarioDTO dto) {
		int flag = 0;
		if (dto != null) {
			usuariotable.delete(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/buscarUsuarios")
	public List<Usuario> getUsuarios() {
		List<Usuario> lista = new ArrayList<>();
		usuariotable.findAll().forEach(lista::add);
		return lista;
	}

	private boolean esValido(UsuarioDTO dto) {
		return dto != null && texto(dto.nombre()) && texto(dto.correo_electronico()) && texto(dto.contrasena());
	}
}
