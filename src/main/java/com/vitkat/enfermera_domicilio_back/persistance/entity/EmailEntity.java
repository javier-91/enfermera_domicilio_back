package com.vitkat.enfermera_domicilio_back.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailEntity {
	
    private String[] toUser;
    private String subject;
    private String message;

}
