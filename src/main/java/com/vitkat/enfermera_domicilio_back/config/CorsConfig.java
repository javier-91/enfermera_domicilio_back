package com.vitkat.enfermera_domicilio_back.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig  implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
	    	.allowedOrigins("http://localhost:4200", "http://192.168.1.35:4200")
	    	.allowedMethods("GET", "POST", "PUT", "DELETE, OPTIONS")
	    	.allowedHeaders("*")
			.allowCredentials(true); // Necesario para cookies
	}

}
