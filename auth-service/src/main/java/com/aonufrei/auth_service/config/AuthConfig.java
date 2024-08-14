package com.aonufrei.auth_service.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

	@Bean
	public PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder(8);
	}

	@Bean
	public Algorithm createAlgorithm(@Value("${secret.key}") String secretKey) {
		return Algorithm.HMAC256(secretKey);
	}
}
