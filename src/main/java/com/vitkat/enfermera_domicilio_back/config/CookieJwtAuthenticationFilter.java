package com.vitkat.enfermera_domicilio_back.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

public class CookieJwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtDecoder jwtDecoder;
	private final String cookieName;

	public CookieJwtAuthenticationFilter(JwtDecoder jwtDecoder, String cookieName) {
		this.jwtDecoder = jwtDecoder;
		this.cookieName = cookieName;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws java.io.IOException, jakarta.servlet.ServletException {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Arrays.stream(cookies).filter(c -> cookieName.equals(c.getName())).findFirst().ifPresent(cookie -> {
				try {
					Jwt jwt = jwtDecoder.decode(cookie.getValue());

					// Extraer roles del claim "scope"
					var authorities = jwt.getClaimAsStringList("scope").stream()
							.map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority(r))
							.toList();

					SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt, authorities));
				} catch (Exception e) {
					// Token inválido, limpiar contexto
					SecurityContextHolder.clearContext();
				}
			});
		}

		filterChain.doFilter(request, response);
	}

}
