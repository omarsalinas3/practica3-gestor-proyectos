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

import com.mx.cbtis.gestor.dto.TareasDTO;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.TareasRepository;

@RestController
public class TareasController {

	private final TareasRepository tareastable;

	public TareasController(TareasRepository tareastable) {
		this.tareastable = tareastable;
	}

	@PostMapping("/getTarea")
	public List<Tareas> getTarea(@RequestBody TareasDTO filtro) {
		return tareastable.findByIdProyecto(filtro.id_proyecto());
	}

	@GetMapping("/buscarTarea")
	public Tareas getTarea(@RequestParam int id_tarea) {
		Tareas buscar = new Tareas();
		Optional<Tareas> encontrado = tareastable.findById(id_tarea);
		if (encontrado.isPresent()) {
			buscar = encontrado.get();
		}
		return buscar;
	}

	@PostMapping("/addTareas")
	public int addTareas(@RequestBody TareasDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			tareastable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/updateTareas")
	public int updateTareas(@RequestBody TareasDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			tareastable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@DeleteMapping("/deleteTareas")
	public int deleteTareas(@RequestBody TareasDTO dto) {
		int flag = 0;
		if (dto != null) {
			tareastable.delete(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/buscarTareas")
	public List<Tareas> getTareas() {
		List<Tareas> lista = new ArrayList<>();
		tareastable.findAll().forEach(lista::add);
		return lista;
	}

	private boolean esValido(TareasDTO dto) {
		return dto != null && texto(dto.nombre()) && dto.id_proyecto() != null;
	}
}
