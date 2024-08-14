package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PatientOutDto {

	private Long id;

	@JsonProperty("account_id")
	private String accountId;

	private String name;

	private String surname;

	@JsonProperty("image_url")
	private String imageUrl;

	@JsonProperty("created_date")
	private LocalDateTime creationDate;

}
