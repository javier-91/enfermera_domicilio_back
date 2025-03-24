package com.vitkat.enfermera_domicilio_back.domain.iservice;

import java.io.File;

public interface EmailService {
	/**
	 * Método para enviar correo.
	 * @param toUser Correo a quien se le va mandar
	 * @param subject Asunto
	 * @param message Mensaje
	 */
    void sendEmail(String[] toUser, String subject, String message);
    /**
     * Método para enviar correo con archivo
     * @param toUser Correo a quien se le va mandar
     * @param subject Asunto
     * @param message Mensaje
     * @param file Archivo
     */
    void sendEmailWithFile(String[] toUser, String subject, String message, File file);

}
