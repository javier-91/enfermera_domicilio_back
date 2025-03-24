package com.vitkat.enfermera_domicilio_back.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitkat.enfermera_domicilio_back.domain.dto.CitaPojo;
import com.vitkat.enfermera_domicilio_back.domain.iservice.CitaService;
import com.vitkat.enfermera_domicilio_back.domain.repository.CitaRepository;
import com.vitkat.enfermera_domicilio_back.persistance.entity.Cita;
import com.vitkat.enfermera_domicilio_back.persistance.mapper.CitaMapper;

@Service
public class CitaServiceImp implements CitaService{

	@Autowired
	CitaRepository citaRepository;
	
	@Autowired
	CitaMapper citaMapper;
	
	@Override
	public CitaPojo save(CitaPojo cita) {
		Cita citaConv = citaMapper.toCita(cita);
		citaRepository.save(citaConv);
		return cita;
	}

	@Override
	public Optional<CitaPojo> findById(Integer id) {
		
		return citaRepository.findById(id)
                .map(citaMapper::toCitaPojo);
               
	}

	@Override
	public List<CitaPojo> findAll() {
		return citaMapper.toCitaPojoList(citaRepository.findAll());
	}

	@Override
	public void deleteById(Integer id) {
		citaRepository.deleteById(id);
	}

}
