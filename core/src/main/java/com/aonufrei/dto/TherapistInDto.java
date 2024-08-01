package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapistInDto {

	@NotNull(message = "Account id is required")
	@JsonProperty("account_id")
	private String accountId;

	@NotBlank(message = "Therapist name is required")
	private String name;

	@NotBlank(message = "Therapist surname is required")
	private String surname;

	@NotBlank(message = "Therapist proficiency is required")
	private String proficiency;

	@JsonProperty("resume_markup")
	private String resumeMarkup;

	@JsonProperty("specialty_ids")
	private List<Long> specialtyIds;

	@JsonProperty("language_ids")
	private List<Long> languageIds;

	@Min(value = 1, message = "Hour price cannot be less than 1 Euro")
	private Integer price;

	private Boolean visible;

	@JsonProperty("image_url")
	private String imageUrl;

	@NotNull(message = "Therapist date of birth is required")
	private LocalDate dob;

}
