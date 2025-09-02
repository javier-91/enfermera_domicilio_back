package com.vitkat.enfermera_domicilio_back.persistance.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class ApiUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiName;         
    private LocalDate date;        
    private int count;              
    
    public ApiUsage() {
    	
    }
    public ApiUsage(String apiName, LocalDate date, int count) {
        this.apiName = apiName;
        this.date = date;
        this.count = count;
    }

}
