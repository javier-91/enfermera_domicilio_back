package com.vitkat.enfermera_domicilio_back.persistance.mapper;

import com.vitkat.enfermera_domicilio_back.domain.dto.EmailFilePojo;
import com.vitkat.enfermera_domicilio_back.persistance.entity.EmailFileEntity;

public interface EmailFileMapper {
	
	/**
	 * Método que convierte las entidades a DTO.
	 * @param citaEntity
	 * @return
	 */
	EmailFilePojo toEmailPojo(EmailFileEntity emailEntity);
	
	/**
	 * Método que convierte los DTO a entidades.
	 * @param citaPojo
	 * @return
	 */
	EmailFileEntity toEmailEntity (EmailFilePojo emailPojo);

}
