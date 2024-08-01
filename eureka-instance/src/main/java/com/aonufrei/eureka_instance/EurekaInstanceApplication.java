package com.aonufrei.eureka_instance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaInstanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaInstanceApplication.class, args);
	}

}
