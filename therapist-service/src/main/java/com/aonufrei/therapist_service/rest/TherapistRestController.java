package com.aonufrei.therapist_service.rest;

import com.aonufrei.dto.*;
import com.aonufrei.therapist_service.service.ScheduleService;
import com.aonufrei.therapist_service.service.TherapistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/therapists")
public class TherapistRestController {

	private static final Logger log = LoggerFactory.getLogger(TherapistRestController.class);
	private final TherapistService therapistService;
	private final ScheduleService scheduleService;

	public TherapistRestController(TherapistService therapistService, ScheduleService scheduleService) {
		this.therapistService = therapistService;
		this.scheduleService = scheduleService;
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
	public List<SearchTherapistOutDto> search(@Validated @RequestBody SearchTherapistDto query) {
		therapistService.modifySearchQuery(query);
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

	@GetMapping("{id}/schedule/day")
	public ScheduleOutDto getDaySchedule(@PathVariable Long id, @RequestParam DayOfWeek dayOfWeek) {
		return scheduleService.getDaySchedule(id, dayOfWeek);
	}

	@GetMapping("{id}/schedule/week")
	public List<ScheduleOutDto> getWeeklySchedule(@PathVariable Long id) {
		return scheduleService.getWeeklySchedule(id);
	}

	@PutMapping("{id}/schedule")
	public List<AvailableSessionOutDto> updateDaySchedule(@PathVariable Long id, @RequestParam DayOfWeek dayOfWeek, @RequestBody List<LocalTime> times) {
		log.info("Updating day schedule");
		return scheduleService.updateDayAvailability(id, dayOfWeek, times);
	}

}
