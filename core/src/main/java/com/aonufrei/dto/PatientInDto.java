package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientInDto {

	@NotNull(message = "Account id is required")
	@JsonProperty("account_id")
	private String accountId;

	@NotBlank(message = "Patient name is required")
	private String name;

	@NotBlank(message = "Patient surname is required")
	private String surname;

	@JsonProperty("image_url")
	private String imageUrl;

	@NotBlank(message = "Patient name is required")
	private String lang;

	@NotNull(message = "Patient date of birth is required")
	private LocalDate dob;

}
