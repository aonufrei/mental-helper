package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchTherapistOutDto {

	private Long id;

	private String name;

	private String surname;

	@JsonProperty("image_url")
	private String imageUrl;

	private String proficiency;

	private Integer price;

	@JsonProperty("language_names")
	private List<String> languageNames;

	@JsonProperty("specialty_titles")
	private List<String> specialtyTitles;

}
