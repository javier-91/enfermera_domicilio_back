package com.vitkat.enfermera_domicilio_back.domain.service;

import java.util.List;
import java.util.Optional;

import com.vitkat.enfermera_domicilio_back.persistance.entity.Cita;

public interface CitaService {
	
	/**
	 * Guardamos la cita
	 * @param cita
	 * @return
	 */
	Cita save(Cita cita);
	/**
	 * Buscamos la cita por el id que se pasa por parámetro.
	 * @param id
	 * @return Devuelve el objeto encontrado, si no, no devuelve nada.
	 */
	Optional<Cita> findById(Integer id);
	/**
	 * Devuelve una lista con todas las citas.
	 * @return
	 */
	List<Cita> findAll();
	/**
	 * Elimina la cita por su id.
	 * @param id
	 */
	void deleteById (Integer id);
	

}
