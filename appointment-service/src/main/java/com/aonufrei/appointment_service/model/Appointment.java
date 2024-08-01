package com.aonufrei.appointment_service.model;

import com.aonufrei.appointment_service.enums.AppointmentState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "patient_id")
	private Long patientId;

	@Column(name = "therapist_id")
	private Long therapistId;

	private AppointmentState state;

	private LocalDateTime time;

	@Column(name = "creation_date")
	@CreationTimestamp
	private LocalDateTime creationDate;

}
