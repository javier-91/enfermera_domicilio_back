package com.vitkat.enfermera_domicilio_back.persistance.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitkat.enfermera_domicilio_back.domain.repository.CitaRepository;
import com.vitkat.enfermera_domicilio_back.persistance.entity.Cita;

public interface CitaRepositoryImp extends JpaRepository<Cita, Integer>, CitaRepository{

}
