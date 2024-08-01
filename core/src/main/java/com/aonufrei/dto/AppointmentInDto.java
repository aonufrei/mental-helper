package com.aonufrei.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentInDto {

	@NotNull(message = "Patient id is required")
	@JsonProperty("patient_id")
	private Long patientId;

	@NotNull(message = "Therapist id is required")
	@JsonProperty("therapist_id")
	private Long therapistId;

	@NotNull(message = "Therapist id is required")
	private LocalDateTime time;

}
