package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableSessionInDto {

	@JsonProperty("therapist_id")
	@NotBlank(message = "Therapist id is required")
	private Long therapistId;

	@JsonProperty("day_of_week")
	@NotNull(message = "Day of week is required")
	private DayOfWeek dayOfWeek;

	@NotNull(message = "Time is required")
	private LocalTime time;

}
