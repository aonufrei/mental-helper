package com.aonufrei.appointment_service.rest;

import com.aonufrei.appointment_service.enums.AppointmentState;
import com.aonufrei.appointment_service.service.AppointmentService;
import com.aonufrei.dto.AppointmentInDto;
import com.aonufrei.dto.AppointmentOutDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentRestController {

	private final AppointmentService service;


	public AppointmentRestController(AppointmentService service) {
		this.service = service;
	}

	@GetMapping("therapist/{id}")
	public List<AppointmentOutDto> getNextTherapistAppointments(@PathVariable("id") Long therapistId) {
		return service.getNextTherapistAppointments(therapistId);
	}

	@GetMapping("patient/{id}")
	public List<AppointmentOutDto> getNextPatientAppointments(@PathVariable("id") Long patientId) {
		return service.getNextPatientAppointments(patientId);
	}

	@PostMapping
	public AppointmentOutDto create(@Validated @RequestBody AppointmentInDto dto) {
		return service.create(dto);
	}

	@PutMapping("{id}")
	public AppointmentOutDto changeState(@PathVariable Long id, @RequestParam("state") AppointmentState state) {
		return service.changeState(id, state);
	}

}
