package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentOutDto {

	private Long id;

	@JsonProperty("patient_id")
	private Long patientId;

	@JsonProperty("therapist_id")
	private Long therapistId;

	private String state;

	private LocalDateTime time;

	private LocalDateTime creationDate;

}
