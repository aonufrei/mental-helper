package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSessionOutDto {

	private Long id;

	@JsonProperty("therapist_id")
	private Long therapistId;

	@JsonProperty("day_of_week")
	private DayOfWeek dayOfWeek;

	private LocalTime time;
}
