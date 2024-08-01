package com.aonufrei.appointment_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper createModelMapper() {
		return new ModelMapper();
	}

	public static ModelMapper createModelMapperWithIgnoredAmbiguity() {
		var mm = new ModelMapper();
		mm.getConfiguration().setAmbiguityIgnored(true);
		return mm;
	}

}
