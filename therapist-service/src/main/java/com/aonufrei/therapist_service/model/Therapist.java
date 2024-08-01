package com.aonufrei.therapist_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "therapist")
@Entity
public class Therapist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "account_id", nullable = false)
	private String accountId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(nullable = false)
	private String proficiency;

	@Column(nullable = false)
	private String resumeMarkup;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Specialty> specialties;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Language> languages;

	@Column(nullable = false)
	private Boolean visible;

	private Integer price;

	private LocalDate dob;

	@Column(name = "creation_date")
	@CreationTimestamp
	private LocalDateTime creationDate;

}
