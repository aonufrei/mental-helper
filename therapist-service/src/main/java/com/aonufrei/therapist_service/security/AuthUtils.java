package com.aonufrei.therapist_service.security;

import com.aonufrei.dto.AccountOutDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class AuthUtils {

	private final Logger log = LoggerFactory.getLogger(AuthUtils.class);

	private final RestTemplate restTemplate;

	public AuthUtils(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public AccountOutDto defineAccount(String token) {
		log.info("Defining Account");
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		var entity = new HttpEntity<>("", headers);
		var response = restTemplate.exchange("http://localhost:8004/api/v1/auth/identity",
				HttpMethod.GET, entity, AccountOutDto.class);
		log.info("Status code: " + response.getStatusCode());
		log.info("Response: " + response.getBody());
		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Failed to identify account");
		}
		return response.getBody();
	}
}
