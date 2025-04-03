package com.vitkat.enfermera_domicilio_back.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class Fecha {
	private LocalDate fecha;
	
	public Fecha(Date fecha) {
		if (fecha == null) {
			throw new IllegalArgumentException("La fecha no puede ser nula");
		}
		this.fecha= fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
	}
	
	public int getDiaMes() {
		return this.fecha.getDayOfMonth();
	} 
	
	public String getDiaSemana() {
		return fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));//TextStyle.FULL obtiene el nombre completo del mes.
	}
	
	public String getMes() {
		return fecha.getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"));//TextStyle.FULL obtiene el nombre completo del mes.
		
	}
	public int getAnyo() {
		return fecha.getYear();
	}
	
	
	


}