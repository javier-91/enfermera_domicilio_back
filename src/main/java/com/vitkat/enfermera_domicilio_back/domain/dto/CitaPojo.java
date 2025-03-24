package com.vitkat.enfermera_domicilio_back.domain.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitaPojo {
	private Integer id;
	private String nom;
	private String correu;
	private String telefon;
	private String missatge;
	private Date data;
	private String hora;

	
}
