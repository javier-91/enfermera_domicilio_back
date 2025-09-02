package com.vitkat.enfermera_domicilio_back.persistance.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitkat.enfermera_domicilio_back.persistance.entity.ApiUsage;

public interface ApiUsageRepository extends JpaRepository<ApiUsage, Long> {
    Optional<ApiUsage> findByApiNameAndDate(String apiName, LocalDate date);
}