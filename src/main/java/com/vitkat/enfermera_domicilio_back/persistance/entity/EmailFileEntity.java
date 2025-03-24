package com.vitkat.enfermera_domicilio_back.persistance.entity;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailFileEntity {
	
    private String[] toUser;
    private String subject;
    private String message;
    private MultipartFile file;

}
