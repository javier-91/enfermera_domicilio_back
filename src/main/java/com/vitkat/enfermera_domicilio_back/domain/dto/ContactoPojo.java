package com.vitkat.enfermera_domicilio_back.domain.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactoPojo {

	private Integer id;
	private String nom;
	private String correu;
	private String telefon;
	private String missatge;
	
	public String toString() {
		return "Cita: \n Nombre = " + nom + "\n" + 
				"Correo = " + correu + "\n" + 
				"Telefono = " + telefon + "\n" + 
				"Mensaje = " + missatge ;
	}
}
