package com.vitkat.enfermera_domicilio_back.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailPojo {

    private String[] toUser;
    private String subject;
    private String message;
}
