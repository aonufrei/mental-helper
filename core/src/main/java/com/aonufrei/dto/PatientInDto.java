package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientInDto {

	@NotBlank(message = "Patient name is required")
	private String name;

	@NotBlank(message = "Patient surname is required")
	private String surname;

	@JsonProperty("image_url")
	private String imageUrl;

}
