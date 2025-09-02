package com.vitkat.enfermera_domicilio_back.domain.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitkat.enfermera_domicilio_back.domain.iservice.GoogleService;
import com.vitkat.enfermera_domicilio_back.persistance.entity.ApiUsage;
import com.vitkat.enfermera_domicilio_back.persistance.repository.ApiUsageRepository;

@Service
public class GoogleServiceImp implements GoogleService {

	@Autowired
	private  GoogleApiKeyProvider apiKeyProvider;
	
	@Autowired
	private ApiUsageRepository apiUsageRepository;

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();

	// Cache en memoria para resultados de autocomplete
	private final Map<String, Map<String, Object>> cacheAutocomplete = new HashMap<>();

	private int contador = 0;

	/**
	 * Obtiene sugerencias de direcciones usando la API de Autocomplete. Implementa
	 * cache y session token para optimizar llamadas.
	 * 
	 * @param input Texto parcial de la dirección
	 * @return Map con resultados
	 */
	@Override
	public Map<String, Object> getAutocomplete(String input) {
		/*String cacheKey = "Cardedeu|" + input;
		
		

		// 1. Revisar si ya está en caché
		if (cacheAutocomplete.containsKey(cacheKey)) {
			return cacheAutocomplete.get(cacheKey);
		}*/

		// 2. Contador de peticiones
		contador++;

		// 3. Token de sesión para Google Places
		String sessionToken = UUID.randomUUID().toString();

		// 4. Construir la URL del request
		String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json" + "?input=" + input
				+ "&types=address" + "&components=country:ES" + "&key=" + apiKeyProvider.getApiKey() + "&sessiontoken=" + sessionToken;

		Map<String, Object> mapResponse;
		try {
			// 5. Llamar a la API de Google
			String response = restTemplate.getForObject(url, String.class);

			// 6. Parsear la respuesta a Map
			mapResponse = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {
			});

			// 7. Extraer predicciones y filtrar solo Cardedeu
			List<Map<String, Object>> predictions = (List<Map<String, Object>>) mapResponse.get("predictions");
			if (predictions != null) {
				/*
				 * List<String> pueblos = List.of("Cardedeu", "Granollers", "La Garriga");
				 * 
				 * predictions = predictions.stream() .filter(p -> { String description =
				 * (String) p.get("description"); return
				 * pueblos.stream().anyMatch(description::contains); }) .toList();
				 */
				predictions = predictions.stream().filter(p -> ((String) p.get("description")).contains("Cardedeu"))
						.toList();
				// Reemplazar predicciones filtradas en la respuesta
				mapResponse.put("predictions", predictions);
			}

			// 8. Guardar en caché el resultado completo (mapResponse filtrado)
			//cacheAutocomplete.put(cacheKey, mapResponse);

		} catch (Exception e) {
			// En caso de error, devolver respuesta vacía
			mapResponse = Map.of("predictions", new Object[0]);
		}

		System.out.println(contador);
		return mapResponse;
	}

	/**
	 * Obtiene la geocodificación de una dirección completa usando la API de
	 * Geocoding. Configurado para España por defecto si se desea.
	 * 
	 * @param address Dirección completa
	 * @return JSON con latitud, longitud y detalles
	 */
	@Override
	public String getGeocoding(String address) {
		String url = "https://maps.googleapis.com/maps/api/geocode/json" + "?address=" + address
				+ "&components=country:ES" + "&key=" + apiKeyProvider.getApiKey();

		return restTemplate.getForObject(url, String.class);
	}
	
	/**
	 * Servicio para controlar las llamadas al servidor google
	 * @param apiName
	 * @param maxPerDay
	 * @return
	 */
	public boolean canCallApi(String apiName, int maxPerDay) {
	    // 1️- Obtener la fecha actual
	    LocalDate today = LocalDate.now();

	    // 2️- Buscar en la base de datos si ya hay un registro de uso de la API para hoy
	    // Si no existe, crear uno nuevo con count=0
	    ApiUsage usage = apiUsageRepository.findByApiNameAndDate(apiName, today)
	                               .orElseGet(() -> new ApiUsage(apiName, today, 0));

	    // 3️- Comprobar si ya se alcanzó el máximo diario
	    if (usage.getCount() >= maxPerDay) {
	        return false; // No permitir más llamadas hoy
	    }

	    // 4️- Incrementar el contador de llamadas y guardar en la base de datos
	    usage.setCount(usage.getCount() + 1);
	    apiUsageRepository.save(usage);

	    // 5️- Permitir la llamada
	    return true;
	}


}
