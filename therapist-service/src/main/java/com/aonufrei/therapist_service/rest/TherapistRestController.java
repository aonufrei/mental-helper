package com.aonufrei.therapist_service.rest;

import com.aonufrei.dto.SearchTherapistOutDto;
import com.aonufrei.dto.TherapistInDto;
import com.aonufrei.dto.TherapistOutDto;
import com.aonufrei.dto.TherapistSearchDto;
import com.aonufrei.therapist_service.service.TherapistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/therapists")
public class TherapistRestController {

	private static final Integer DEFAULT_PAGE_SIZE = 10;

	private static final Logger log = LoggerFactory.getLogger(TherapistRestController.class);
	private final TherapistService therapistService;

	public TherapistRestController(TherapistService therapistService) {
		this.therapistService = therapistService;
	}


	@GetMapping("{id}")
	public ResponseEntity<TherapistOutDto> getById(@PathVariable Long id) {
		return therapistService.findOutById(id).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().body(null));
	}


	@PostMapping
	public ResponseEntity<TherapistOutDto> create(@Validated @RequestBody TherapistInDto specialty) {
		log.info("Create new therapist");
		return ResponseEntity.ok(therapistService.create(specialty));
	}

	@PutMapping("{id}")
	public ResponseEntity<TherapistOutDto> update(@PathVariable Long id, @Validated @RequestBody TherapistInDto specialty) {
		log.info("Update existing therapist with id [" + id + "]");
		return ResponseEntity.ok(therapistService.update(id, specialty));
	}

	@GetMapping
	public List<TherapistOutDto> findAll() {
		return therapistService.getAll();
	}

	@GetMapping("/search")
	public List<SearchTherapistOutDto> search(@Validated @RequestBody TherapistSearchDto query) {
		if (query.getPageSize() == null || query.getPageSize() < 0) {
			query.setPageSize(DEFAULT_PAGE_SIZE);
		}
		if (query.getPageNumber() == null || query.getPageNumber() < 0) {
			query.setPageNumber(0);
		}
		return therapistService.search(query);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {
		log.info("Delete therapist with id [{}]", id);
		try {
			therapistService.delete(id);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.badRequest().body(false);
		}
		return ResponseEntity.ok().body(true);
	}
	
}
