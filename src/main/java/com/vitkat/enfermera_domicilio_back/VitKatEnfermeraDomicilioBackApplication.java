package com.vitkat.enfermera_domicilio_back;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.vitkat.enfermera_domicilio_back.config.RsaKeysConfig;


@SpringBootApplication
@EnableConfigurationProperties(RsaKeysConfig.class)//Habilita la carga de propiedades desde la clase RSA
public class VitKatEnfermeraDomicilioBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(VitKatEnfermeraDomicilioBackApplication.class, args);
	

	}
	
	

}
