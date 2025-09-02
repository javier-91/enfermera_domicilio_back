package com.vitkat.enfermera_domicilio_back.domain.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPojo {
	
    private String username;
    private String password;
    private String nom;
    private String correu;
    private String telefon;
    private Set<String> roles;
}
