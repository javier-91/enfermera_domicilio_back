package com.vitkat.enfermera_domicilio_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitkat.enfermera_domicilio_back.domain.dto.CitaPojo;
import com.vitkat.enfermera_domicilio_back.domain.iservice.CitaService;


import lombok.RequiredArgsConstructor;


/**
 * Controlador REST de citas
 */
@RestController
@RequestMapping("/citas")
@CrossOrigin("*") // Permite que el frontend acceda al backend sin problemas de CORS
public class CitaController {

	private final CitaService citaService;
    @Autowired
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }
	
	@GetMapping()
	public ResponseEntity<List<CitaPojo>> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(citaService.findAll());
		
	}
	
	@PostMapping()
	public ResponseEntity<CitaPojo> save(@RequestBody CitaPojo cita) {
		return ResponseEntity.status(HttpStatus.OK).body(citaService.save(cita));
	}

}
