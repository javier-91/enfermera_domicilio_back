package com.vitkat.enfermera_domicilio_back.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * Vinculamos propiedades de configuracion del archivo properties con campos de la clase RsaKeysConfig
 */
@ConfigurationProperties(prefix = "rsa")
public record RsaKeysConfig(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
