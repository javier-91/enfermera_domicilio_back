package com.vitkat.enfermera_domicilio_back.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitkat.enfermera_domicilio_back.persistance.entity.Cita;

public interface CitaRepository extends JpaRepository<Cita, Integer>{
	

}
