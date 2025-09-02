package com.vitkat.enfermera_domicilio_back.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true") // Permite que el frontend acceda al backend con cookies
public class UserController {

    // Endpoint de prueba para verificar protección
    @GetMapping("/pruebas")
    public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("¡Accediste al endpoint protegido!");
    }

    /**
     * Enpoint para obtener nombre del usuario y roles.
     * @param authentication
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfoUser(Authentication authentication) {
    	System.out.print("ENTRA");
        Object principal = authentication.getPrincipal();
        
        String username;
        Map<String, Object> claims;
        
        if (principal instanceof Jwt jwt) {
            username = jwt.getSubject();
            claims = jwt.getClaims();
        } else {
            // Si viene de UserDetailsService
            username = authentication.getName();
            claims = Map.of(); // No hay claims JWT
        }

        Map<String, Object> userInfo = Map.of(
            "username", username,
            "roles", authentication.getAuthorities().stream()
                                   .map(GrantedAuthority::getAuthority)
                                   .toList(),
            "claims", claims
        );
        System.out.print("ENTRA" + userInfo);
        return ResponseEntity.ok(userInfo);
    }

    
    @GetMapping("/infoComp")
    public ResponseEntity<Map<String, Object>> getInfoCompUser(Authentication authentication){
    	String nombre=authentication.getName();
    	
    	return null;
    }
    
}
