package com.vitkat.enfermera_domicilio_back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitkat.enfermera_domicilio_back.domain.dto.CitaPojo;
import com.vitkat.enfermera_domicilio_back.domain.iservice.CitaService;
import com.vitkat.enfermera_domicilio_back.domain.iservice.EmailService;
import com.vitkat.enfermera_domicilio_back.utils.Fecha;

import jakarta.validation.Valid;

/**
 * Controlador REST de citas
 */
@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
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

	/**
	 * Obtiene la lista completa de citas.
	 * 
	 * @return ResponseEntity con la lista de CitaPojo y el estado HttpStatusOK.
	 */
	@GetMapping()
	public ResponseEntity<List<CitaPojo>> getAll() {
		System.out.println("Entra en citas");
		return ResponseEntity.status(HttpStatus.OK).body(citaService.findAll());

	}

	/**
	 * Crea una nueva cita y envía correos de confirmación.
	 * 
	 * @param cita Recibe el objeto en el cuerpo de la petición.
	 * @return un ResponseEntity con la cita creada y el estado HttpStatusOK.
	 */
	@PostMapping()
	public ResponseEntity<CitaPojo> save(@RequestBody CitaPojo cita) {
		System.out.println(cita.getEnfermera() + cita.getMinutosServicio());
		System.out.print(cita.toString());
		citaService.save(cita);
		Fecha fecha = new Fecha(cita.getData());
		emailService.sendEmail(cita.getCorreu(), "Confirmación de su cita.", "Estimado/a " + cita.getNom() + ",<br><br>"
				+ "Le confirmamos que su cita ha sido agendada con éxito.<br>" + "📅 *Fecha:* " + fecha.getDiaSemana()
				+ " " + fecha.getDiaMes() + ", de " + fecha.getMes() + " del " + fecha.getAnyo() + "<br>" + "⏰ *Hora:* "
				+ cita.getHora() + "<br>" + "📧 *Dirección:* " + cita.getDireccio() + "<br>"
				+ "ℹ️ <i>Si necesita modificar o cancelar su cita, por favor contacte con nosotros con antelación.</i><br>"
				+ "🔄 <i>Recuerde que, por motivos de agenda, la hora o fecha podrían ser ajustadas. Agradecemos su comprensión.</i><br><br>"
				+ "📞 Teléfono de contacto: 666414342<br>" + "📧 Correo de contacto: " + emailAdmin + "<br><br>"
				+ "Gracias por confiar en nosotros.<br><br>" + "Atentamente,\n" + "VitKat");
		emailService.sendEmail(emailAdmin, "Cita", cita.toString());

		return ResponseEntity.status(HttpStatus.OK).body(cita);
	}

	/**
	 * Actualiza una cita existente por su id
	 * 
	 * @param id   Integer, Identificador cita actualizar.
	 * @param cita objeto CitaPojo con los nuevos dats.
	 * @return una ResponseEntity con la cita actualizada y el estado HttpStatusOK
	 */
	@PutMapping("/{id}")
	public ResponseEntity<CitaPojo> update(@Valid @PathVariable Integer id, @RequestBody CitaPojo cita) {
		System.out.println("UPDATE" + cita.toString());
		citaService.updateCita(cita, id);

		return ResponseEntity.status(HttpStatus.OK).body(cita);

	}

	/**
	 * Elimina una cita existente.
	 * 
	 * @param id Integer
	 * @return ResponseEntity sin contenido, 204 se elimina, 404 no existe.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		if (!citaService.existsById(id)) {
			return ResponseEntity.notFound().build(); // 404 no existe
		}
		citaService.deleteById(id);
		return ResponseEntity.noContent().build(); // 204 se elimina
	}

}
