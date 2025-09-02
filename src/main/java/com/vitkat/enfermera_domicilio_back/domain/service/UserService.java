package com.vitkat.enfermera_domicilio_back.domain.service;

import com.vitkat.enfermera_domicilio_back.domain.dto.UserPojo;
import com.vitkat.enfermera_domicilio_back.persistance.entity.RoleEntity;
import com.vitkat.enfermera_domicilio_back.persistance.entity.RoleEnum;
import com.vitkat.enfermera_domicilio_back.persistance.entity.UserEntity;
import com.vitkat.enfermera_domicilio_back.persistance.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public UserEntity createUser(UserPojo userPojo) {
		// Verifica si el usuario ya existe
		if (userRepository.findUserEntityByUsername(userPojo.getUsername()).isPresent()) {
			throw new RuntimeException("El usuario ya existe");
		}

		// Construimos nuestro DTO a Entidad
		Set<RoleEntity> roles = userPojo.getRoles().stream()
				.map(role -> RoleEntity.builder().roleEnum(RoleEnum.valueOf(role)).build()).collect(Collectors.toSet());

		UserEntity userEntity = UserEntity.builder().username(userPojo.getUsername())
				.password(passwordEncoder.encode(userPojo.getPassword())).nom(userPojo.getNom())
				.correu(userPojo.getCorreu()).telefon(userPojo.getTelefon()).roles(roles).build();

		// Guarda en la base de datos
		return userRepository.save(userEntity);
	}

	@Transactional
	public Optional<UserEntity> deleteUser(String username) {
		// Buscamos el usuario por username
		Optional<UserEntity> userOpt = userRepository.findUserEntityByUsername(username);

		// Si existe, lo eliminamos
		userOpt.ifPresent(userRepository::delete);

		// Retornamos el Optional: vacío si no existía, con el usuario si se eliminó
		return userOpt;
	}

}
