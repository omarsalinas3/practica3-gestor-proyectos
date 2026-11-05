package com.mx.cbtis.gestor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.cbtis.gestor.dto.AsignacionesDTO;
import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;

@RestController
public class AsignacionesController {

	private final AsignacionesRepository asignacionestable;

	public AsignacionesController(AsignacionesRepository asignacionestable) {
		this.asignacionestable = asignacionestable;
	}

	@GetMapping("/buscarAsignacion")
	public Asignaciones getAsignacion(@RequestParam int id) {
		Asignaciones buscar = new Asignaciones();
		Optional<Asignaciones> encontrado = asignacionestable.findById(id);
		if (encontrado.isPresent()) {
			buscar = encontrado.get();
		}
		return buscar;
	}

	@PostMapping("/addAsignacion")
	public int addAsignacion(@RequestBody AsignacionesDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			asignacionestable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/updateAsignacion")
	public int updateAsignacion(@RequestBody AsignacionesDTO dto) {
		int flag = 0;
		if (esValido(dto)) {
			asignacionestable.save(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@DeleteMapping("/deleteAsignacion")
	public int deleteAsignacion(@RequestBody AsignacionesDTO dto) {
		int flag = 0;
		if (dto != null) {
			asignacionestable.delete(dto.toEntity());
			flag = 1;
		}
		return flag;
	}

	@PostMapping("/buscarAsignaciones")
	public List<Asignaciones> getAsignaciones() {
		List<Asignaciones> lista = new ArrayList<>();
		asignacionestable.findAll().forEach(lista::add);
		return lista;
	}

	private boolean esValido(AsignacionesDTO dto) {
		return dto != null && dto.id_usuario() != null && dto.id_tarea() != null;
	}
}
