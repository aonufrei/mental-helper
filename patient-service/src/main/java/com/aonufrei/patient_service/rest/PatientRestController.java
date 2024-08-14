package com.aonufrei.patient_service.rest;

import com.aonufrei.dto.PatientInDto;
import com.aonufrei.dto.PatientOutDto;
import com.aonufrei.dto.RegisterPersonDto;
import com.aonufrei.patient_service.model.Patient;
import com.aonufrei.patient_service.service.PatientService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/patients")
public class PatientRestController {

	private static final Logger log = LoggerFactory.getLogger(PatientRestController.class);

	private final PatientService patientService;

	public PatientRestController(PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping("{id}")
	public ResponseEntity<PatientOutDto> byId(@PathVariable Long id) {
		log.info("Getting patient with id [{}]", id);
		var patientById = patientService.findOutById(id);
		return patientById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
	}

	@PostMapping("register")
	public ResponseEntity<PatientOutDto> registerPatient(@Validated @RequestBody RegisterPersonDto<PatientInDto> payload) {
		return ResponseEntity.ok(patientService.register(payload));
	}

	@PostMapping
	public ResponseEntity<PatientOutDto> create(@RequestParam String accountId, @Validated @RequestBody PatientInDto patientIn) {
		log.info("Creating new patient");
		var patient = patientService.create(accountId, patientIn);
		return ResponseEntity.ok(patient);
	}

	@PutMapping("{id}")
	public ResponseEntity<PatientOutDto> update(@PathVariable Long id, @Validated @RequestBody PatientInDto patientIn) {
		log.info("Updating existing patient with id [{}]", id);
		var patient = patientService.update(id, patientIn);
		return ResponseEntity.ok(patient);
	}

	@DeleteMapping("{id}")
	public boolean delete(@PathVariable Long id) {
		log.info("Delete patient with id [{}]", id);
		try {
			patientService.delete(id);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
		return true;
	}

}
