package com.vitkat.enfermera_domicilio_back.persistance.mapper;



import java.util.List;

import org.mapstruct.Mapper;

import com.vitkat.enfermera_domicilio_back.domain.dto.CitaPojo;
import com.vitkat.enfermera_domicilio_back.persistance.entity.Cita;

@Mapper(componentModel = "spring")
public interface CitaMapper {
	
	/**
	 * Método que convierte las entidades a DTO.
	 * @param citaEntity
	 * @return
	 */
	CitaPojo toCitaPojo(Cita citaEntity);
	
	/**
	 * Método que convierte los DTO a entidades.
	 * @param citaPojo
	 * @return
	 */
	Cita toCita (CitaPojo citaPojo);
	/**
	 * Método que convierte una lista de entidades a una lista DTO.
	 * @param citas
	 * @return
	 */
	List<CitaPojo> toCitaPojoList (List<Cita> citas);
	/**
	 * Método que convierte una lista en DTO a una lista de entidades.
	 * @param citasPojo
	 * @return
	 */
	List<Cita> toCitaList (List<CitaPojo>citasPojo);

}
