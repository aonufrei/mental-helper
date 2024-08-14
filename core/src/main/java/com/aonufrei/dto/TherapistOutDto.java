package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapistOutDto {

	private Long id;

	@JsonProperty("account_id")
	private String accountId;

	private String name;

	private String surname;

	private String proficiency;

	@JsonProperty("resume_markup")
	private String resumeMarkup;

	private List<SpecialtyOutDto> specialties;

	private List<LanguageOutDto> languages;

	private Boolean visible;

	@JsonProperty("image_url")
	private String imageUrl;

	private Integer price;

	@JsonProperty("creation_date")
	private LocalDateTime creationDate;

}
