package com.vitkat.enfermera_domicilio_back.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
public class GoogleApiKeyProvider {

    private final String apiKey;

    public GoogleApiKeyProvider(@Value("classpath:/certs/google-api-key.txt") Resource resource) throws Exception {
        this.apiKey = new String(Files.readAllBytes(resource.getFile().toPath())).trim();
    }

    public String getApiKey() {
        return apiKey;
    }
}
