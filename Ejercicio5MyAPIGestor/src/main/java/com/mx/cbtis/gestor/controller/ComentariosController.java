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

import com.mx.cbtis.gestor.dto.ComentariosDTO;
import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.repository.ComentariosRepository;

@RestController
public class ComentariosController {

	private final ComentariosRepository comentariostable;

	public ComentariosController(ComentariosRepository comentariostable) {
		this.comentariostable = comentariostable;
	}

	@PostMapping("/getComentario")
	public List<Comentarios> getComentario(@RequestBody ComentariosDTO filtro) {
		return comentariostable.findByTarea(filtro.id_tarea());
	}

	@GetMapping("/buscarComentario")
	public Comentarios getComentario(@RequestParam int id) {
		Comentarios buscar = new Comentarios();
		Optional<Comentarios> encontrado = comentariostable.findById(id);
		if (encontrado.isPresent()) {
			buscar = encontrado.get();
		}
		return buscar;
	}

	@PostMapping("/addComentario")
	public int addComentario(@RequestBody ComentariosDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			comentariostable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/updateComentario")
	public int updateComentario(@RequestBody ComentariosDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			comentariostable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@DeleteMapping("/deleteComentario")
	public int deleteComentario(@RequestBody ComentariosDTO dto) {
		int flag = 0;
		if (dto != null) {
			comentariostable.delete(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/buscarComentarios")
	public List<Comentarios> getComentarios() {
		List<Comentarios> lista = new ArrayList<>();
		comentariostable.findAll().forEach(lista::add);
		return lista;
	}

	private boolean esValido(ComentariosDTO dto) {
		return dto != null && texto(dto.id_usuario()) && dto.id_tarea() != null && texto(dto.texto_comentario());
	}
}
