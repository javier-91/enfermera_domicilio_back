package com.vitkat.enfermera_domicilio_back.persistance.mapper;

import org.mapstruct.Mapper;


import com.vitkat.enfermera_domicilio_back.domain.dto.EmailPojo;
import com.vitkat.enfermera_domicilio_back.persistance.entity.EmailEntity;

@Mapper(componentModel = "spring")
public interface EmailMapper {

	/**
	 * Método que convierte las entidades a DTO.
	 * @param citaEntity
	 * @return
	 */
	EmailPojo toEmailPojo(EmailEntity emailEntity);
	
	/**
	 * Método que convierte los DTO a entidades.
	 * @param citaPojo
	 * @return
	 */
	EmailEntity toEmailEntity (EmailPojo emailPojo);
}
