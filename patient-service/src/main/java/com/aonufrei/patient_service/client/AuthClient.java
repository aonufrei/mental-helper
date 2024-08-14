package com.aonufrei.patient_service.client;

import com.aonufrei.dto.AccountInDto;
import com.aonufrei.dto.AccountOutDto;
import com.aonufrei.dto.RegisterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class AuthClient {

	private static final String AUTH_SERVICE = "http://auth-service/api/v1/auth";

	private static final Logger log = LoggerFactory.getLogger(AuthClient.class);

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public AuthClient(@Qualifier("balanced") RestTemplate restTemplate, ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public Optional<String> registerAccount(RegisterDto account) {
		var response = restTemplate.postForEntity(AUTH_SERVICE + "/register?role=PATIENT", account, String.class);
		if (!response.getStatusCode().is2xxSuccessful()) {
			return Optional.empty();
		}
		try {
			var resp = objectMapper.readValue(response.getBody(), AccountOutDto.class);
			return Optional.of(resp.getId());
		} catch (JsonProcessingException e) {
			log.error("Failed to process auth response", e);
			return Optional.empty();
		}
	}

	public boolean deleteAccount(String accountId) {
		var response = restTemplate.exchange(AUTH_SERVICE + "?accountId=" + accountId,
				HttpMethod.DELETE, null, String.class);
		return response.getStatusCode().is2xxSuccessful();
	}


}
