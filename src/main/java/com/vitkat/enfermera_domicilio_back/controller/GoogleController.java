package com.vitkat.enfermera_domicilio_back.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.vitkat.enfermera_domicilio_back.domain.iservice.GoogleService;

@RestController
@RequestMapping("/google")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class GoogleController {
	
	private static final int MAX_PLACES_PER_DAY = 500;     
	private static final int MAX_GEOCODE_PER_DAY = 500;
	private static final int MAX_MAPLOAD_PER_DAY = 1000;

	@Autowired
	private GoogleService mapsService;


	/**
	 * Obtiene sugerencias de direcciones (Autocomplete) utilizando la API de Google
	 * Places.
	 * 
	 * Este endpoint recibe un texto parcial de una dirección ingresada por el
	 * usuario y devuelve un mapa con las predicciones generadas por Google. El
	 * resultado ya viene como Map desde el servicio, por lo que Angular puede
	 * procesarlo directamente.
	 * 
	 * Se recomienda que el texto tenga al menos 3 caracteres para obtener
	 * resultados relevantes. El método maneja internamente cache y token de sesión
	 * para optimizar llamadas a la API y reducir costos.
	 * 
	 * @param input Texto parcial de la dirección que se desea autocompletar.
	 * @return Map<String, Object> con la información de predicciones de
	 *         direcciones. En caso de error en la llamada a Google o si no hay
	 *         resultados, devuelve un mapa con la clave "predictions" asociada a
	 *         una lista vacía.
	 */
	@GetMapping("/autocomplete")
	public Map<String, Object> autocomplete(@RequestParam String input) {
	    if (!mapsService.canCallApi("Places", MAX_PLACES_PER_DAY)) {
	        return Map.of("predictions", new Object[0], "status", "DAILY_LIMIT_EXCEEDED");
	    }
		System.out.println(input);
		Map<String, Object> response = mapsService.getAutocomplete(input); // ya es Map
		System.out.println(response);
		return response; // retorno directo, sin ObjectMapper
	}

	/**
	 * Endpoint para obtener la geocodificación de una dirección completa.
	 * 
	 * @param address Dirección completa en texto (ejemplo: "Av. Aroma #123,
	 *                Cochabamba, Bolivia")
	 * @return JSON en formato String con latitud, longitud y detalles de la
	 *         dirección
	 */
	@GetMapping("/geocode")
	public String geocode(@RequestParam String address) {
	    if (!mapsService.canCallApi("Places", MAX_PLACES_PER_DAY)) {
	        return ("DAILY_LIMIT_EXCEEDED");
	    }
		return mapsService.getGeocoding(address);
	}

}
