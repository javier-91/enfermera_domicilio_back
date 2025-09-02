package com.vitkat.enfermera_domicilio_back.domain.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.vitkat.enfermera_domicilio_back.domain.iservice.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImp implements EmailService {

	@Value("${email.sender}")
	private String emailUser;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Async
	public CompletableFuture<Void> sendEmail(String toUser, String subject, String message) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
			mailMessage.setFrom(emailUser);// Correo que esta configurado en la clase config
			mailMessage.setTo(toUser);
			mailMessage.setSubject(subject);
			mailMessage.setText("<html><body>" + message + "<br><img src='cid:logo' style='width:25px; height:auto;'/>"
					+ "<br><br></body></html>", true);
			Resource resource = new ClassPathResource("img/logo.png");
			File logo = resource.getFile();
			mailMessage.addInline("logo", logo);

			mailSender.send(mimeMessage);
			//log.info("Correo enviado con éxito a: {}", toUser);
			return CompletableFuture.completedFuture(null);
		} catch (IOException e) {
			//log.error("Error leyendo el archivo logo.png: {}", e.getMessage(), e);
			throw new RuntimeException("No se pudo cargar el logo.", e);
		} catch (MessagingException e) {
			//log.error("Error enviando correo a {}: {}", toUser, e.getMessage(), e);
			return CompletableFuture.completedFuture(null);
		}
	}


}
