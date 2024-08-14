package com.aonufrei.patient_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "patient")
@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "account_id", nullable = false)
	private String accountId;

	@Column(nullable = false)
	private String name;

	private String surname;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "creation_date")
	@CreationTimestamp
	private LocalDateTime creationDate;

}
