package com.vitkat.enfermera_domicilio_back.persistance.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase para crear objetos de citas del cliente.
 */
@Entity
@Table(name ="citas")
public class Cita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nom;
	private String correu;
	private int telefon;
	private String missatge;
	private Date data;
	private int hora;
	
	public Cita() {
		
	}

	public Cita(Integer id, String nom, String correu, int telefon, String missatge, Date data, int hora) {
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

	public int getTelefon() {
		return telefon;
	}

	public void setTelefon(int telefon) {
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

	public int getHora() {
		return hora;
	}

	public void setHora(int hora) {
		this.hora = hora;
	}
	
}
