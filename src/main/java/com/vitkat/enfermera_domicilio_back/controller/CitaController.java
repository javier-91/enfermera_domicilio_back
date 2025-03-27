package com.vitkat.enfermera_domicilio_back.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.vitkat.enfermera_domicilio_back.domain.iservice.EmailService;
import com.vitkat.enfermera_domicilio_back.utils.Fecha;



/**
 * Controlador REST de citas
 */
@RestController
@RequestMapping("/citas")
@CrossOrigin("*") // Permite que el frontend acceda al backend sin problemas de CORS
public class CitaController {

	private final CitaService citaService;
	
	private final EmailService emailService;
	
    @Value("${email.sender}")
    private String emailAdmin;
	
    @Autowired
    public CitaController(CitaService citaService, EmailService emailService) {
        this.citaService = citaService;
        this.emailService = emailService;
    }
	
	@GetMapping()
	public ResponseEntity<List<CitaPojo>> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(citaService.findAll());
		
	}
	
	@PostMapping()
	public ResponseEntity<CitaPojo> save(@RequestBody CitaPojo cita) {
		
		System.out.println(cita.getData());
		citaService.save(cita);
		Fecha fecha = new Fecha(cita.getData());
		emailService.sendEmail(
			    cita.getCorreu(), 
			    "Confirmación de su cita.", 
			    "Estimado/a " + cita.getNom() + ",<br><br>" +
			    "Le confirmamos que su cita ha sido agendada con éxito.<br>" +
			    "📅 *Fecha:* "+ fecha.getDiaSemana()+" "+ fecha.getDiaMes() +", de "+fecha.getMes()+ " del "+fecha.getAnyo()+"<br>" +
			    "⏰ *Hora:* " + cita.getHora() + "<br>" +
			    "Si necesita modificar o cancelar su cita, no dude en contactarnos.<br>" +
			    "📞 Teléfono de contacto: 666414342<br>" +
			    "📧 Correo de contacto: "+emailAdmin+"<br><br>" +
			    "Gracias por confiar en nosotros.<br><br>" +
			    "Atentamente,\n" +
			    "VitKat"
			);
		emailService.sendEmail(emailAdmin, "Cita", cita.toString());
		
		String response = "Todo a ido correcto";
		
		return ResponseEntity.status(HttpStatus.OK).body(cita);
	}

}
