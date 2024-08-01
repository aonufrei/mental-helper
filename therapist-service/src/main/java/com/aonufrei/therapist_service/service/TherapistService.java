package com.aonufrei.therapist_service.service;

import com.aonufrei.dto.SearchTherapistOutDto;
import com.aonufrei.dto.TherapistInDto;
import com.aonufrei.dto.TherapistOutDto;
import com.aonufrei.dto.TherapistSearchDto;
import com.aonufrei.therapist_service.model.Language;
import com.aonufrei.therapist_service.model.Specialty;
import com.aonufrei.therapist_service.model.Therapist;
import com.aonufrei.therapist_service.repo.TherapistRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TherapistService {

	private final ModelMapper modelMapper;
	private final TherapistRepo therapistRepo;

	private final LanguageService languageService;
	private final SpecialtyService specialtyService;

	public TherapistService(ModelMapper modelMapper, TherapistRepo therapistRepo,
	                        LanguageService languageService, SpecialtyService specialtyService) {
		this.modelMapper = modelMapper;
		this.therapistRepo = therapistRepo;
		this.languageService = languageService;
		this.specialtyService = specialtyService;
	}

	public List<TherapistOutDto> getAll() {
		return therapistRepo.findAll().stream()
				.map(this::toOut).collect(Collectors.toList());
	}

	public List<SearchTherapistOutDto> search(TherapistSearchDto query) {
		var page = Pageable.ofSize(query.getPageSize()).withPage(query.getPageNumber());
		return therapistRepo.search(query.getLanguages(), query.getSpecialties(), page).stream()
				.map(this::toSearchOut)
				.collect(Collectors.toList());
	}


	public Optional<Therapist> findById(Long id) {
		return therapistRepo.findById(id);
	}

	public Optional<TherapistOutDto> findOutById(Long id) {
		return findById(id).map(this::toOut);
	}

	public TherapistOutDto create(TherapistInDto therapistInDto) {
		Therapist model = toModel(therapistInDto);
		model.setCreationDate(LocalDateTime.now());
		var created = therapistRepo.save(model);
		return toOut(created);
	}

	public TherapistOutDto update(Long id, TherapistInDto therapistInDto) {
		if (!therapistRepo.existsById(id)) {
			throw createTherapistNotFoundException(id);
		}
		var model = toModel(therapistInDto);
		model.setId(id);
		var created = therapistRepo.save(model);
		return toOut(created);
	}

	public void delete(Long id) {
		var toDelete = findById(id).orElseThrow(() -> createTherapistNotFoundException(id));
		therapistRepo.delete(toDelete);
	}


	public Therapist toModel(TherapistInDto therapistInDto) {
		var model = modelMapper.map(therapistInDto, Therapist.class);
		model.setLanguages(languageService.findAllWithIds(therapistInDto.getLanguageIds()));
		model.setSpecialties(specialtyService.findAllWithIds(therapistInDto.getSpecialtyIds()));
		return model;
	}

	public TherapistOutDto toOut(Therapist therapist) {
		var out = modelMapper.map(therapist, TherapistOutDto.class);
		out.setLanguages(languageService.toLanguageOutList(therapist.getLanguages()));
		out.setSpecialties(specialtyService.toSpecialtyOutList(therapist.getSpecialties()));
		return out;
	}

	public SearchTherapistOutDto toSearchOut(Therapist model) {
		return SearchTherapistOutDto.builder()
				.id(model.getId())
				.name(model.getName())
				.surname(model.getSurname())
				.proficiency(model.getProficiency())
				.price(model.getPrice())
				.imageUrl(model.getImageUrl())
				.languageNames(model.getLanguages().stream().map(Language::getTitle).collect(Collectors.toList()))
				.specialtyTitles(model.getSpecialties().stream().map(Specialty::getName).collect(Collectors.toList()))
				.build();
	}

	private RuntimeException createTherapistNotFoundException(Long id) {
		return new RuntimeException("Therapist with id [" + id + "] does not exist");
	}

}
