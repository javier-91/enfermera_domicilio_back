package com.vitkat.enfermera_domicilio_back.domain.iservice;

import java.util.List;
import java.util.Optional;

import com.vitkat.enfermera_domicilio_back.domain.dto.CitaPojo;
public interface CitaService {

	/**
	 * Guardamos la cita
	 * 
	 * @param cita
	 * @return
	 */
	CitaPojo save(CitaPojo cita);

	/**
	 * Buscamos la cita por el id que se pasa por parámetro.
	 * 
	 * @param id
	 * @return Devuelve el objeto encontrado, si no, no devuelve nada.
	 */
	Optional<CitaPojo> findById(Integer id);

	/**
	 * Devuelve una lista con todas las citas.
	 * 
	 * @return
	 */
	List<CitaPojo> findAll();

	/**
	 * Elimina la cita por su id.
	 * 
	 * @param id
	 */
	void deleteById(Integer id);
	
	/**
	 * Primero busca la cita por su id y finalmente actualiza los cambios.
	 * @param cita
	 * @param id
	 * @return Devuelve el objecto actualizado "CitaPojo" si no, no devuelve nada.
	 */
	Optional<CitaPojo> updateCita(CitaPojo cita, Integer id);
	
	/**
	 * Busca el objeto Cita por su id, si lo encuentra retorna true, si no retorna false.
	 * @param id Integer
	 * @return boolean
	 */
	boolean existsById(Integer id);

}
