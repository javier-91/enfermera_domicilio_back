package com.vitkat.enfermera_domicilio_back.domain.dto;

import java.sql.Date;

public class CitaPojo {
	private Integer id;
	private String nom;
	private String correu;
	private int telefon;
	private String missatge;
	private Date data;
	private int hora;
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
