package com.aonufrei.therapist_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TherapistServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TherapistServiceApplication.class, args);
	}

}
