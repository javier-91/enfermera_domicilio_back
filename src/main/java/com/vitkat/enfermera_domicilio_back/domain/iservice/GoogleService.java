package com.vitkat.enfermera_domicilio_back.domain.iservice;

import java.util.Map;

public interface GoogleService {
	
	/**
	 * Obtiene el autocompletado de direcciones usando la API de google.
	 * @param input Texto que ingresa el usuario.
	 * @return Un map con las sugerencias de las direcciones.
	 */
	public Map<String, Object> getAutocomplete(String input);
	/**
	 * Obtiene la dirección completa usando la API.
	 * @param address 
	 * @return
	 */
	public String getGeocoding(String address);
	
	/**
	 * Método para saber si hemos excedido el maximo de consultas por dia.
	 * @param apiName Nombre de la API GOOGLE
	 * @param maxPerDay MAXIMO POR DIA
	 * @return
	 */
	public boolean canCallApi(String apiName, int maxPerDay);
	
}
