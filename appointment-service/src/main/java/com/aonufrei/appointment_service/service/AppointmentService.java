package com.aonufrei.appointment_service.service;

import com.aonufrei.appointment_service.enums.AppointmentState;
import com.aonufrei.appointment_service.model.Appointment;
import com.aonufrei.appointment_service.repo.AppointmentRepo;
import com.aonufrei.dto.AppointmentInDto;
import com.aonufrei.dto.AppointmentOutDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aonufrei.appointment_service.config.ModelMapperConfig.createModelMapperWithIgnoredAmbiguity;

@Service
public class AppointmentService {

	private final ModelMapper modelMapper;
	private final ModelMapper noAmbiguityCheckMM = createModelMapperWithIgnoredAmbiguity();
	private final AppointmentRepo repo;

	public AppointmentService(ModelMapper modelMapper, AppointmentRepo repo) {
		this.modelMapper = modelMapper;
		this.repo = repo;
	}

	public AppointmentOutDto create(AppointmentInDto appointment) {
		var model = toModel(appointment);
		model.setState(AppointmentState.CREATED);
		model.setCreationDate(LocalDateTime.now());
		var saved = repo.save(model);
		return toOut(saved);
	}

	public List<AppointmentOutDto> getNextTherapistAppointments(Long therapistId) {
		var appointments = repo.findAllOfTherapist(
				therapistId,
				AppointmentState.ACCEPTED,
				LocalDateTime.now(),
				Pageable.ofSize(100)
		);
		return appointments.stream().map(this::toOut).collect(Collectors.toList());
	}

	public List<AppointmentOutDto> getNextPatientAppointments(Long patientId) {
		var appointments = repo.findAllOfPatient(
				patientId,
				AppointmentState.ACCEPTED,
				LocalDateTime.now(),
				Pageable.ofSize(100)
		);
		return appointments.stream().map(this::toOut).collect(Collectors.toList());
	}

	public AppointmentOutDto changeState(Long id, AppointmentState state) {
		var appointment = repo.findById(id).orElseThrow(() -> createNotFoundException(id));
		appointment.setState(state);
		return toOut(repo.save(appointment));
	}

	public Appointment toModel(AppointmentInDto dto) {
		return noAmbiguityCheckMM.map(dto, Appointment.class);
	}

	public AppointmentOutDto toOut(Appointment model) {
		var out = modelMapper.map(model, AppointmentOutDto.class);
		out.setState(Optional.ofNullable(model.getState()).map(Objects::toString).orElse(null));
		return out;
	}

	private RuntimeException createNotFoundException(Long id) {
		return new RuntimeException("Appointment of id [" + id + "] was not found");
	}

}
