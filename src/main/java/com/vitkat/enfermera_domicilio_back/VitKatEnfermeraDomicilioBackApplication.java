package com.vitkat.enfermera_domicilio_back;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;

import com.vitkat.enfermera_domicilio_back.persistance.entity.PermissionEntity;
import com.vitkat.enfermera_domicilio_back.persistance.entity.RoleEntity;
import com.vitkat.enfermera_domicilio_back.persistance.entity.RoleEnum;
import com.vitkat.enfermera_domicilio_back.persistance.entity.UserEntity;
import com.vitkat.enfermera_domicilio_back.persistance.repository.UserRepository;


@SpringBootApplication
public class VitKatEnfermeraDomicilioBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(VitKatEnfermeraDomicilioBackApplication.class, args);
	

	}
	
	@Bean
	CommandLineRunner init (UserRepository userRepository) {
		return args -> {
			/* CREATE PERMISSION */
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();
			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();
			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();
			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();
			
			/* CREATE ROLES */
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.roles(Set.of(createPermission,readPermission,updatePermission, deletePermission))
					.build();
			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.roles(Set.of(createPermission,readPermission))
					.build();
			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.roles(Set.of(createPermission,readPermission,updatePermission, deletePermission))
					.build();
			RoleEntity roleInvited = RoleEntity.builder()
					.roleEnum(RoleEnum.INVITED)
					.roles(Set.of(readPermission))
					.build();
			
			/*CREATE USERS */
            UserEntity userSantiago = UserEntity.builder()
                    .username("santiago")
                    .password("$2a$10$GhJjURkjx6TgHGYGIvg1w.EMSRKDsYqhDcUlhfoF3AR6WQ.5ygC0m")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleAdmin))
                    .build();

            UserEntity userDaniel = UserEntity.builder()
                    .username("daniel")
                    .password("$2a$10$GhJjURkjx6TgHGYGIvg1w.EMSRKDsYqhDcUlhfoF3AR6WQ.5ygC0m")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleUser))
                    .build();

            UserEntity userAndrea = UserEntity.builder()
                    .username("andrea")
                    .password("$2a$10$GhJjURkjx6TgHGYGIvg1w.EMSRKDsYqhDcUlhfoF3AR6WQ.5ygC0m")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleInvited))
                    .build();

            UserEntity userAnyi = UserEntity.builder()
                    .username("anyi")
                    .password("$2a$10$GhJjURkjx6TgHGYGIvg1w.EMSRKDsYqhDcUlhfoF3AR6WQ.5ygC0m")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleDeveloper))
                    .build();
            UserEntity userXavi = UserEntity.builder()
                    .username("xavi")
                    .password("1234")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleDeveloper))
                    .build();


            userRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi, userXavi));
		};
	}

}
