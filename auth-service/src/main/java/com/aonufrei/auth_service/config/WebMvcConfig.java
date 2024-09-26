package com.aonufrei.auth_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/v1/auth/**")
				.allowedOriginPatterns("*")
				.allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")
				.allowCredentials(true);
	}

}
