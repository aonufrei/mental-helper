package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchTherapistDto {

	private List<Long> languages;

	private List<Long> specialties;

	@JsonProperty("from_price")
	private Integer fromPrice;

	@JsonProperty("to_price")
	private Integer toPrice;

	@Min(value = 1, message = "Page size cannot be less than 1")
	@Max(value = 100, message = "Page size cannot be bigger than 100")
	private Integer pageSize;

	@Min(value = 1, message = "Page number cannot be less than 1")
	private Integer pageNumber;

}
