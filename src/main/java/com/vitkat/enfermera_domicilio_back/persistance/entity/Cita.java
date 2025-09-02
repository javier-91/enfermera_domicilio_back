package com.vitkat.enfermera_domicilio_back.persistance.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase para crear objetos de citas del cliente.
 */
@Entity
@Table(name ="citas")
@ToString
@Getter
@Setter
public class Cita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "El nombre no puede ser nulo")
	@Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
	private String nom;
	@NotBlank(message ="El correo no puede estar vacío.")
	@Email(message = "El correo no es correcto.")
	private String correu;
	@Pattern(regexp = "^[0-9]{9}$", message = "El número debe tener exactamente 9 dígitos")
	private String telefon;
	private String missatge;
	private Date data;
	@NotBlank(message = "La hora no puede ser nulo ni vacía.")
	private String hora;
	@NotBlank(message = "Tienes que seleccionar una enfermera.")
	private String enfermera;
	@NotBlank(message = "La dirección no puede estar vacía.")
	private String direccio;
	@NotNull
	private Integer minutosServicio;
	
	
	public Cita() {
		
	}

	public Cita(Integer id, String nom, String correu, String telefon, String missatge, Date data, String hora, String enfermera, String direccio, Integer minutosServicio) {
		super();
		this.id = id;
		this.nom = nom;
		this.correu = correu;
		this.telefon = telefon;
		this.missatge = missatge;
		this.data = data;
		this.hora = hora;
		this.enfermera = enfermera;
		this.direccio = direccio;
		this.minutosServicio = minutosServicio;
		
	}

}
