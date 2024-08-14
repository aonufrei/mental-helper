package com.aonufrei.patient_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

	@Bean("balanced")
	@LoadBalanced
	public RestTemplate createRest() {
		return new RestTemplate();
	}

}
