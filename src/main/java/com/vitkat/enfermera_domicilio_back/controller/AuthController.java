package com.vitkat.enfermera_domicilio_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true") // Permite que el frontend acceda al backend sin problemas de CORS con credenciales
public class AuthController {

	// Inyecta el encoder y decoder de JWT para generar y verificar los tokens
	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private JwtDecoder jwtDecoder;

	// Inyecta el AuthenticationManager que se encarga de la autenticación
	@Autowired
	private AuthenticationManager authenticationManager;

	// Inyecta el UserDetailsService que se utiliza para obtener los detalles del
	// usuario
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 *  Endpoint para generar el token, ya sea por autenticación con usuario y
	 *  contraseña o por un refresh token
	 */
	@PostMapping("/token")
	public ResponseEntity<Map<String, String>> generarToken(String grantType, String username, String password,
			boolean withRefreshToken, String refreshToken, HttpServletResponse response ) {

		// Inicializa las variables que contendrán los datos del usuario y los roles
		// (scope)
		String subject = null;
		String scope = null;

		// Si el tipo de concesión es "password", realiza la autenticación con el
		// usuario y contraseña proporcionados
		if (grantType.equals("password")) {
			// Autenticamos al usuario con las credenciales proporcionadas (username,
			// password)
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			// Una vez autenticado, obtenemos el nombre del usuario (subject)
			subject = authentication.getName();

			// Obtenemos los roles del usuario y los concatenamos para formar el "scope" del
			// token
			scope = authentication.getAuthorities().stream().map(aut -> aut.getAuthority())
					.collect(Collectors.joining(" "));
		}
		// Si el tipo de concesión es "refreshToken", validamos el refresh token para
		// generar un nuevo access token
		else if (grantType.equals("refreshToken")) {
			// Si no se proporciona el refreshToken, respondemos con un error
			if (refreshToken == null) {
				return new ResponseEntity<>(Map.of("errorMessage", "El refresh token es requerido"),
						HttpStatus.UNAUTHORIZED);
			}

			Jwt decodeJWT = null;
			try {
				// Decodificamos el refresh token para extraer la información del usuario
				decodeJWT = jwtDecoder.decode(refreshToken);
			} catch (JwtException exception) {
				// Si el refresh token no es válido, respondemos con un error
				return new ResponseEntity<>(Map.of("errorMessage", exception.getMessage()), HttpStatus.UNAUTHORIZED);
			}

			// Obtenemos el nombre del usuario (subject) desde el refresh token decodificado
			subject = decodeJWT.getSubject();

			// Cargamos los detalles del usuario para obtener sus roles (scope)
			UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
			scope = authorities.stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
		}

		// Creamos el mapa que contendrá los tokens
		Map<String, String> idToken = new HashMap<>();

		// Generamos el tiempo actual
		Instant instant = Instant.now();

		// Creamos el conjunto de reclamaciones para el JWT Access Token
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder().subject(subject) // El usuario autenticado
				.issuedAt(instant) // Hora de emisión
				.expiresAt(instant.plus(withRefreshToken ? 1 : 5, ChronoUnit.MINUTES)) // Establecemos el tiempo de
																						// expiración (5 minutos si no
																						// hay refresh token, 1 minuto
																						// si sí)
				.issuer("security-service") // El emisor del token
				.claim("scope", scope) // Los roles/alcances del usuario
				.build();

		// Generamos el Access Token usando el encoder
		String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
		//idToken.put("accessToken", jwtAccessToken); // Añadimos el Access Token al mapa de respuesta
		// Creamos la cookie para el access token
        Cookie jwtCookie = new Cookie("accessToken", jwtAccessToken);
        jwtCookie.setHttpOnly(true); // Impide que JavaScript acceda a la cookie
        //jwtCookie.setSecure(true); // Solo se enviará sobre HTTPS
        jwtCookie.setPath("/"); // La cookie será accesible desde cualquier ruta
        jwtCookie.setMaxAge(60 * 60); // Duración de la cookie (1 hora)
        response.addCookie(jwtCookie); // Agregamos la cookie a la respuesta
		// Si el parámetro withRefreshToken es verdadero, generamos también un Refresh
		// Token
		if (withRefreshToken) {
			// Creamos el conjunto de reclamaciones para el Refresh Token
			JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder().subject(subject) // El usuario autenticado
					.issuedAt(instant) // Hora de emisión
					.expiresAt(instant.plus(5, ChronoUnit.MINUTES)) // Establecemos el tiempo de expiración (5 minutos
																	// para el refresh token)
					.issuer("security-service") // El emisor del token
					.claim("scope", scope) // Los roles/alcances del usuario
					.build();

			// Generamos el Refresh Token usando el encoder
			String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
			idToken.put("refreshToken", jwtRefreshToken); // Añadimos el Refresh Token al mapa de respuesta
		}

		// Devolvemos los tokens generados como respuesta
		//return new ResponseEntity<>(idToken, HttpStatus.OK);
		
		// Respondemos sin cuerpo, ya que los tokens están siendo enviados a través de las cookies
        return new ResponseEntity<>(HttpStatus.OK);
	}
}
