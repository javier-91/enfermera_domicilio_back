package com.vitkat.enfermera_domicilio_back.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitkat.enfermera_domicilio_back.domain.dto.CitaPojo;
import com.vitkat.enfermera_domicilio_back.domain.dto.UserPojo;
import com.vitkat.enfermera_domicilio_back.domain.iservice.CitaService;
import com.vitkat.enfermera_domicilio_back.domain.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.35:4200"}, allowCredentials = "true") // Permite que el frontend acceda al backend,
																			// sin problemas de CORS con credenciales
public class AdminController {

	private final UserService userService;
	private final CitaService citaService;

	@PostMapping("/create")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserPojo userPojo) {
		try {
			userService.createUser(userPojo);
			return ResponseEntity.ok("Usuario creado correctamente");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error al crear usuario: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteUser(@Valid @RequestParam String id) {
		return userService.deleteUser(id).map(user -> ResponseEntity.noContent().<Void>build()) // 204
				.orElseGet(() -> ResponseEntity.notFound().build()); // 404
	}

	@PostMapping("/updateCitas")
	public ResponseEntity<CitaPojo> updateCitas(@Valid @RequestBody CitaPojo cita, Integer id) {
		return citaService.updateCita(cita, id).map(ResponseEntity::ok) // Si existe → 200 OK con el objeto actualizado
				.orElseGet(() -> ResponseEntity.notFound().build()); // Si no existe → 404

	}
	// PRUEBAS
	@GetMapping("/pruebas")
	public ResponseEntity<String> protectedEndpoint() {
		return ResponseEntity.ok("¡Accediste al endpoint protegido!");
	}

}
