package com.aonufrei.therapist_service.service;

import com.aonufrei.dto.AvailableSessionOutDto;
import com.aonufrei.dto.ScheduleOutDto;
import com.aonufrei.therapist_service.model.AvailableSession;
import com.aonufrei.therapist_service.repo.AvailableSessionRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

	private final AvailableSessionRepo repo;
	private final ModelMapper modelMapper;

	private final TherapistService therapistService;

	public ScheduleService(AvailableSessionRepo repo, ModelMapper modelMapper, TherapistService therapistService) {
		this.repo = repo;
		this.modelMapper = modelMapper;
		this.therapistService = therapistService;
	}

	@Transactional
	public List<AvailableSessionOutDto> updateDayAvailability(Long therapistId,
	                                                           DayOfWeek dayOfWeek,
	                                                           List<LocalTime> sessionTimes) {
		var therapist = therapistService.findById(therapistId)
				.orElseThrow(() -> new RuntimeException("Therapist was not found"));
		var sessions = sessionTimes.stream()
				.filter(Objects::nonNull)
				.map(it -> AvailableSession.builder()
						.dayOfWeek(dayOfWeek)
						.therapist(therapist)
						.time(it)
						.build())
				.toList();
		repo.deleteByTherapistAndDayOfWeek(therapist, dayOfWeek);
		return repo.saveAll(sessions).stream().map(this::toOut).collect(Collectors.toList());
	}

	public ScheduleOutDto getDaySchedule(Long therapistId, DayOfWeek dayOfWeek) {
		var sessions = repo.findByTherapist_IdAndDayOfWeek(therapistId, dayOfWeek);
		var sessionsByDay = sessions.stream().collect(Collectors.groupingBy(AvailableSession::getDayOfWeek));
		return ScheduleOutDto.builder()
				.dayOfWeek(dayOfWeek)
				.time(toSessionTimes(sessionsByDay.getOrDefault(dayOfWeek, Collections.emptyList())))
				.build();

	}

	public List<ScheduleOutDto> getWeeklySchedule(Long therapistId) {
		var sessions = repo.findByTherapist_Id(therapistId);
		var sessionsByDay = sessions.stream().collect(Collectors.groupingBy(AvailableSession::getDayOfWeek));
		return sessionsByDay.entrySet().stream()
				.map(it -> ScheduleOutDto.builder()
						.dayOfWeek(it.getKey())
						.time(toSessionTimes(it.getValue()))
						.build()
				).sorted(Comparator.comparing(ScheduleOutDto::getDayOfWeek))
				.toList();
	}

	public List<LocalTime> toSessionTimes(List<AvailableSession> sessions) {
		return sessions.stream().map(AvailableSession::getTime).collect(Collectors.toList());
	}

	public AvailableSessionOutDto toOut(AvailableSession availableSession) {
		return modelMapper.map(availableSession, AvailableSessionOutDto.class);
	}
}
