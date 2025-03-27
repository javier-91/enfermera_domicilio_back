package com.vitkat.enfermera_domicilio_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.vitkat.enfermera_domicilio_back.domain.dto.ContactoPojo;
import com.vitkat.enfermera_domicilio_back.domain.iservice.EmailService;

/**
 * Controlador REST de contacto
 */
@RestController
@RequestMapping("/contacto")
@CrossOrigin("*") // Permite que el frontend acceda al backend sin problemas de CORS
public class ContactoController {
	
	private final EmailService emailService;
	
    @Autowired
    public ContactoController(EmailService emailService) {
        this.emailService = emailService;
    }
	
    @Value("${email.sender}")
    private String emailAdmin;
    
	@PostMapping()
	public ResponseEntity<ContactoPojo> save(@RequestBody ContactoPojo contacto) {
		

		emailService.sendEmail(emailAdmin, "Contacto", contacto.toString());
		
		String response = "Todo a ido correcto";
		
		return ResponseEntity.status(HttpStatus.OK).body(contacto);
	}

}
