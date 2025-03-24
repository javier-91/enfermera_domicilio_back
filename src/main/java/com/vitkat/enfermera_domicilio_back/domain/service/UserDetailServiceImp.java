package com.vitkat.enfermera_domicilio_back.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vitkat.enfermera_domicilio_back.persistance.entity.UserEntity;
import com.vitkat.enfermera_domicilio_back.persistance.repository.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findUserEntityByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Eo usuario " + username + " no existe"));
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

		userEntity.getRoles().forEach(
				role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

		userEntity.getRoles().stream().flatMap(role -> role.getRoles().stream())
				.forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

		return new User(userEntity.getUsername(), 
				userEntity.getPassword(), 
				userEntity.isEnabled(),
				userEntity.isAccountNoExpired(), 
				userEntity.isCredentialNoExpired(), 
				userEntity.isAccountNoLocked(),
				authorityList);
	}

}
