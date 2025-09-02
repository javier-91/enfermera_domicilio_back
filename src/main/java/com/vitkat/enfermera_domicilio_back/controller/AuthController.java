package com.vitkat.enfermera_domicilio_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true") // Permite que el frontend acceda al backend,
																			// sin problemas de CORS con credenciales
@RequestMapping("/token")
public class AuthController {

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private JwtDecoder jwtDecoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping()
	public ResponseEntity<Map<String, String>> generarToken(String grantType, String username, String password,
			boolean withRefreshToken, String refreshToken, HttpServletResponse response) {

		String subject = null; // Nombre del usuario
		String scope = null; // Roles del usuario

		if ("password".equals(grantType)) {
			// Autenticación con usuario y contraseña
			Authentication authentication;
			try {
				authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			} catch (Exception e) {
				System.out.println("Error en autenticación: " + e.getMessage());
				return new ResponseEntity<>(Map.of("errorMessage", e.getMessage()), HttpStatus.UNAUTHORIZED);
			}

			subject = authentication.getName();
			scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(" "));
		} else if ("refreshToken".equals(grantType)) {
			// Autenticación mediante refresh token
			if (refreshToken == null) {
				return new ResponseEntity<>(Map.of("errorMessage", "El refresh token es requerido"),
						HttpStatus.UNAUTHORIZED);
			}

			Jwt decodeJWT;
			try {
				decodeJWT = jwtDecoder.decode(refreshToken);
			} catch (JwtException exception) {
				return new ResponseEntity<>(Map.of("errorMessage", exception.getMessage()), HttpStatus.UNAUTHORIZED);
			}

			subject = decodeJWT.getSubject();
			UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
			scope = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
		}

		// Generación de tokens
		Map<String, String> idToken = new HashMap<>();
		Instant instant = Instant.now();

		// ====================
		// Access Token
		// ====================
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder().subject(subject).issuedAt(instant)
				.expiresAt(instant.plus(15, ChronoUnit.MINUTES)) // Access token 15 minutos
				.issuer("security-service").claim("scope", scope).build();

		String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

		// ====================
		// Access token en cookie HttpOnly + SameSite=Strict
		// ====================
		ResponseCookie accessCookie = ResponseCookie.from("accessToken", jwtAccessToken).httpOnly(true) // No accesible
																										// desde JS
				// .secure(true) // DESCOMENTAR AL PASAR PRODUCCIÓN (solo HTTPS)
				//.sameSite("strict")//DESCOMENTAR AL PASAR PRODUCCION
				.secure(false)
				.httpOnly(true)
				.path("/") // Toda la aplicación
				.maxAge(60 * 15) // 15 minutos
				.sameSite("Lax") // Protección CSRF
				.build();
		response.addHeader("Set-Cookie", accessCookie.toString());

		if (withRefreshToken) {
			// ====================
			// Refresh Token
			// ====================
			JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder().subject(subject).issuedAt(instant)
					.expiresAt(instant.plus(7, ChronoUnit.DAYS)) // Refresh token 7 días
					.issuer("security-service").claim("scope", scope).build();

			String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();

			// Refresh token también en cookie HttpOnly + SameSite=Strict
			ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", jwtRefreshToken).httpOnly(true)
					// .secure(true) // DESCOMENTAR AL PASAR PRODUCCIÓN
					.secure(false)
					.httpOnly(true)
					.path("/")
					.maxAge(7 * 24 * 60 * 60) // 7 días
					.sameSite("Lax")
					//.sameSite("Strict") DESCOMENTAR AL PASAR PRODUCCION
					.build();
			response.addHeader("Set-Cookie", refreshCookie.toString());
		}

		return ResponseEntity.ok(Map.of("status", "ok"));

	}

	@PostMapping("/refresh")
	public ResponseEntity<Map<String, String>> refreshToken(
			@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
		if (refreshToken == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			Jwt decoded = jwtDecoder.decode(refreshToken);
			String username = decoded.getSubject();

			// Generar nuevo accessToken
			Instant now = Instant.now();
			JwtClaimsSet claims = JwtClaimsSet.builder().subject(username).issuedAt(now)
					.expiresAt(now.plus(15, ChronoUnit.MINUTES)).issuer("security-service").build();

			String newAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

			// Guardarlo en cookie HttpOnly
			ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken).httpOnly(true).path("/")
					.maxAge(60 * 15)
					//.sameSite("Strict") DESCOMENTAR AL PASAR PRODUCCION
					.sameSite("Lax")
					.httpOnly(true)
					.secure(false)
					// .secure(true) // DESCOMENTAR AL PASAR PRODUCCIÓN
					.build();

			response.addHeader("Set-Cookie", accessCookie.toString());
			return ResponseEntity.ok(Map.of("message", "Access token renovado"));
		} catch (JwtException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Refresh token inválido"));
		}
	}

}
