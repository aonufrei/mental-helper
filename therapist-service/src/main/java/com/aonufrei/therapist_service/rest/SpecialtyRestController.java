package com.aonufrei.therapist_service.rest;

import com.aonufrei.dto.SpecialtyInDto;
import com.aonufrei.dto.SpecialtyOutDto;
import com.aonufrei.therapist_service.service.SpecialtyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/specialties")
public class SpecialtyRestController {

	private final static Logger log = LoggerFactory.getLogger(SpecialtyRestController.class);
	private final SpecialtyService specialtyService;

	public SpecialtyRestController(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

	@PostMapping
	public ResponseEntity<SpecialtyOutDto> create(@Validated @RequestBody SpecialtyInDto specialty) {
		log.info("Create new speciality");
		return ResponseEntity.ok(specialtyService.create(specialty));
	}

	@PutMapping("{id}")
	public ResponseEntity<SpecialtyOutDto> update(@PathVariable Long id, @Validated @RequestBody SpecialtyInDto specialty) {
		log.info("Update existing speciality with id [" + id + "]");
		return ResponseEntity.ok(specialtyService.update(id, specialty));
	}

	@GetMapping
	public List<SpecialtyOutDto> findAll() {
		return specialtyService.getAll();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {
		log.info("Delete specialty with id [{}]", id);
		try {
			specialtyService.delete(id);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.badRequest().body(false);
		}
		return ResponseEntity.ok().body(true);
	}
}
