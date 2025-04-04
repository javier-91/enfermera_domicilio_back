package com.vitkat.enfermera_domicilio_back.config;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.vitkat.enfermera_domicilio_back.domain.service.UserDetailServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
//@EnableMethodSecurity Para trbajar con anotaciones en el controller, 
//@PreAuthorize("hasAuthority('READ')") --> esto va en los endpoints del controller
//@PreAuthorize("permitAll()")
//@PreAuthorize("denyAll()")
public class SecurityConfig {

    @Autowired
    private RsaKeysConfig rsaKeysConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        		.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // Configurar los endpoints publicos
                    http.requestMatchers(HttpMethod.POST, "/citas").permitAll();
                	http.requestMatchers(HttpMethod.POST, "/contacto").permitAll();
                	http.requestMatchers( "/token/**").permitAll();
                	http.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    // Configurar los endpoints privados
                    //http.requestMatchers(HttpMethod.POST, "/citas").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers("/admin/**").hasRole("ADMIN");
                    http.requestMatchers("/user/**").hasRole("USER");
                    // Configurar el resto de endpoint - NO ESPECIFICADOS
                    http.anyRequest().denyAll();
                })
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }

    //Decodificar y validar tokens JWT entrantes (Usan la clave pública)
    @Bean
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeysConfig.publicKey()).build();
    }

    //Genera y firma los tokens JWT salientes (Clave privada y pública)
    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKeysConfig.publicKey()).privateKey(rsaKeysConfig.privateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImp userDetailService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    public static void main (String [] args) {
//    	System.out.println(new BCryptPasswordEncoder().encode("1234"));
//    	
//    }
}