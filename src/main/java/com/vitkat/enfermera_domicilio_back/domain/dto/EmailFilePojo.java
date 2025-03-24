package com.vitkat.enfermera_domicilio_back.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailFilePojo {
	
    private String[] toUser;
    private String subject;
    private String message;
    private MultipartFile file;

}
