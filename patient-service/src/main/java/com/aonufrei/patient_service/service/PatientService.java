package com.aonufrei.patient_service.service;

import com.aonufrei.dto.PatientInDto;
import com.aonufrei.dto.PatientOutDto;
import com.aonufrei.patient_service.model.Patient;
import com.aonufrei.patient_service.repo.PatientRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PatientService {

	private final ModelMapper modelMapper;
	private final PatientRepo repo;

	public PatientService(ModelMapper modelMapper, PatientRepo repo) {
		this.modelMapper = modelMapper;
		this.repo = repo;
	}

	public PatientOutDto create(PatientInDto patient) {
		Patient model = toModel(patient);
		model.setCreationDate(LocalDateTime.now());
		var created = repo.save(model);
		return toOut(created);
	}

	public PatientOutDto update(Long id, PatientInDto patient) {
		if (!repo.existsById(id)) {
			throw createPatientNotFoundException(id);
		}
		Patient model = toModel(patient);
		model.setId(id);
		var updated = repo.save(model);
		return toOut(updated);
	}

	public Optional<Patient> findById(Long id) {
		return repo.findById(id);
	}

	public Optional<PatientOutDto> findOutById(Long id) {
		return repo.findById(id).map(this::toOut);
	}

	public void delete(Long id) {
		var toDelete = findById(id).orElseThrow(() -> createPatientNotFoundException(id));
		repo.delete(toDelete);
	}

	private RuntimeException createPatientNotFoundException(Long id) {
		return new RuntimeException("Patient with id [" + id + "] does not exist");
	}

	public Patient toModel(PatientInDto patientInDto) {
		return modelMapper.map(patientInDto, Patient.class);
	}

	public PatientOutDto toOut(Patient patient) {
		return modelMapper.map(patient, PatientOutDto.class);
	}

}
