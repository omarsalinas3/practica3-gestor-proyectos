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

import com.mx.cbtis.gestor.dto.ProyectosDTO;
import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.repository.ProtectosRepository;

@RestController
public class ProyectosController {

	private final ProtectosRepository proyectostable;

	public ProyectosController(ProtectosRepository proyectostable) {
		this.proyectostable = proyectostable;
	}

	@GetMapping("/buscarProyecto")
	public Proyectos getProyecto(@RequestParam int id_proyecto) {
		Proyectos buscar = new Proyectos();
		Optional<Proyectos> encontrado = proyectostable.findById(id_proyecto);
		if (encontrado.isPresent()) {
			buscar = encontrado.get();
		}
		return buscar;
	}

	@PostMapping("/addProyecto")
	public int addProyecto(@RequestBody ProyectosDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			proyectostable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/updateProyecto")
	public int updateProyecto(@RequestBody ProyectosDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			proyectostable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@DeleteMapping("/deleteProyecto")
	public int deleteProyecto(@RequestBody ProyectosDTO dto) {
		int flag = 0;
		if (dto != null) {
			proyectostable.delete(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/buscarProyectos")
	public List<Proyectos> getProyectos() {
		List<Proyectos> lista = new ArrayList<>();
		proyectostable.findAll().forEach(lista::add);
		return lista;
	}

	private boolean esValido(ProyectosDTO dto) {
		return dto != null && texto(dto.nombre());
	}
}
