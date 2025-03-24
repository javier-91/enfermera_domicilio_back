package com.vitkat.enfermera_domicilio_back.persistance.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Clase para crear objetos de citas del cliente.
 */
@Entity
@Table(name ="citas")
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
	
	public Cita() {
		
	}

	public Cita(Integer id, String nom, String correu, String telefon, String missatge, Date data, String hora) {
		super();
		this.id = id;
		this.nom = nom;
		this.correu = correu;
		this.telefon = telefon;
		this.missatge = missatge;
		this.data = data;
		this.hora = hora;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCorreu() {
		return correu;
	}

	public void setCorreu(String correu) {
		this.correu = correu;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getMissatge() {
		return missatge;
	}

	public void setMissatge(String missatge) {
		this.missatge = missatge;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	
}
