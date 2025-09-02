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

import jakarta.transaction.Transactional;

@Service
public class CitaServiceImp implements CitaService{

	@Autowired
	CitaRepository citaRepository;
	
	@Autowired
	CitaMapper citaMapper;
	
	@Override
	@Transactional
	public CitaPojo save(CitaPojo cita) {
		System.out.println(cita.getEnfermera());
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
	@Transactional
	public void deleteById(Integer id) {
		citaRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<CitaPojo> updateCita(CitaPojo citaPojo, Integer id) {
		System.out.println(citaPojo.toString());
		System.out.println("ID"+id);
		return citaRepository.findById(id)
				.map(citaVieja -> {
					System.out.println("ANTES: " + citaVieja);
					System.out.println("ANTES: " + citaPojo);
					citaMapper.updateCitaFromPojo(citaPojo, citaVieja);//Guarda los cambios a la citaPojo existente
					System.out.println("DESPUÉS: " + citaVieja);
					System.out.println("DESPUÉS: " + citaPojo);
					//Cita cita=citaMapper.toCita(citaPojo);
	                Cita citaAct=citaRepository.save(citaVieja);
	                return citaMapper.toCitaPojo(citaAct);						
				});
	}

	@Override
	public boolean existsById(Integer id) {
		return citaRepository.existsById(id);
	}
	

}
