package com.aonufrei.therapist_service.rest;

import com.aonufrei.dto.LanguageInDto;
import com.aonufrei.dto.LanguageOutDto;
import com.aonufrei.therapist_service.service.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/languages")
public class LanguageRestController {

	private final Logger log = LoggerFactory.getLogger(LanguageRestController.class);

	private final LanguageService languageService;

	public LanguageRestController(LanguageService languageService) {
		this.languageService = languageService;
	}

	@GetMapping
	public List<LanguageOutDto> getAll() {
		log.info("Getting all languages ");
		return languageService.getAll();
	}

	@GetMapping("{id}")
	public ResponseEntity<LanguageOutDto> byId(@PathVariable Long id) {
		log.info("Getting language with id [{}]", id);
		return languageService.findOutById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().body(null));
	}

	@PostMapping
	public ResponseEntity<LanguageOutDto> create(@Validated @RequestBody LanguageInDto language) {
		log.info("Creating new language");
		return ResponseEntity.ok(languageService.create(language));
	}

	@PutMapping("{id}")
	public ResponseEntity<LanguageOutDto> update(@PathVariable Long id, @Validated @RequestBody LanguageInDto language) {
		log.info("Updating existing language with id [{}]", id);
		return ResponseEntity.ok(languageService.update(id, language));
	}

	@DeleteMapping("{id}")
	public boolean delete(@PathVariable Long id) {
		log.info("Delete language with id [{}]", id);
		try {
			languageService.delete(id);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
		return true;
	}

}
