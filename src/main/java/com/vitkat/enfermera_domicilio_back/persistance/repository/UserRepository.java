package com.vitkat.enfermera_domicilio_back.persistance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitkat.enfermera_domicilio_back.persistance.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	Optional<UserEntity> findUserEntityByUsername(String name);
	//Optional<UserEntity> findByUsername(String username);
	
	//TAMBIÉN S EPUEDE HACER ASI
	/*@Query("Select usuari from UserEntity usuari WHERE usuari.username = ?")
	Optional<UserEntity> findUser(String username);*/

}
