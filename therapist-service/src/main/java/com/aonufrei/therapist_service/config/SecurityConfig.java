package com.aonufrei.therapist_service.config;

import com.aonufrei.therapist_service.filter.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final TokenFilter tokenFilter;

	public SecurityConfig(TokenFilter tokenFilter) {
		this.tokenFilter = tokenFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.httpBasic(AbstractHttpConfigurer::disable);
		http.formLogin(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(authz -> authz
				.requestMatchers(HttpMethod.GET, "api/v1/search", "api/v1/languages", "api/v1/specialties", "api/v1/therapists/*").permitAll()
				.anyRequest().authenticated()
		);
		http.addFilterAt(tokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
