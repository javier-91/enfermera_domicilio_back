package com.vitkat.enfermera_domicilio_back.domain.iservice;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public interface EmailService {
	/**
	 * Método para enviar correo.
	 * @param toUser Correo a quien se le va mandar
	 * @param subject Asunto
	 * @param message Mensaje
	 */
	CompletableFuture<Void> sendEmail(String toUser, String subject, String message);
   
}
