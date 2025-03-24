package com.vitkat.enfermera_domicilio_back.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class MailConfiguration {
	
    @Value("${email.sender}")
    private String emailUser;

    @Value("${email.password}")
    private String password;
	@Bean
	public JavaMailSender getJavaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("VitKatinfo@gmail.com");
		mailSender.setPassword("password");
		
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");//Protocolo que vamos a utilizar
        props.put("mail.smtp.auth", "true");//Habilitamos la autenticación
        props.put("mail.smtp.starttls.enable", "true");//Habilitamos el cifrado entre la aplicación y el correo mediante SMTP
        props.put("mail.debug", "true");//Imprimirá la información en la consola, es como un log.

        return mailSender;
	}

}
