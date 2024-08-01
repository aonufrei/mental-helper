package com.aonufrei.therapist_service.service;

import com.aonufrei.dto.LanguageInDto;
import com.aonufrei.dto.LanguageOutDto;
import com.aonufrei.therapist_service.model.Language;
import com.aonufrei.therapist_service.repo.LanguageRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LanguageService {

	private final ModelMapper modelMapper;
	private final LanguageRepo languageRepo;

	public LanguageService(ModelMapper modelMapper, LanguageRepo languageRepo) {
		this.modelMapper = modelMapper;
		this.languageRepo = languageRepo;
	}

	public List<LanguageOutDto> getAll() {
		return languageRepo.findAll().stream()
				.map(this::toOut).collect(Collectors.toList());
	}

	public Optional<Language> findById(Long id) {
		return languageRepo.findById(id);
	}

	public Optional<LanguageOutDto> findOutById(Long id) {
		return findById(id).map(this::toOut);
	}

	public List<Language> findAllWithIds(List<Long> ids) {
		return languageRepo.findAllWithIds(ids);
	}

	public LanguageOutDto create(LanguageInDto LanguageInDto) {
		var created = languageRepo.save(toModel(LanguageInDto));
		return toOut(created);
	}

	public LanguageOutDto update(Long id, LanguageInDto LanguageInDto) {
		if (!languageRepo.existsById(id)) {
			throw createLanguageNotFoundException(id);
		}
		var model = toModel(LanguageInDto);
		model.setId(id);
		var created = languageRepo.save(model);
		return toOut(created);
	}

	public void delete(Long id) {
		var toDelete = findById(id).orElseThrow(() -> createLanguageNotFoundException(id));
		languageRepo.delete(toDelete);
	}


	public Language toModel(LanguageInDto languageInDto) {
		return modelMapper.map(languageInDto, Language.class);
	}


	public LanguageOutDto toOut(Language language) {
		return modelMapper.map(language, LanguageOutDto.class);
	}

	public List<LanguageOutDto> toLanguageOutList(List<Language> languages) {
		if (languages == null) return Collections.emptyList();
		return languages.stream()
				.map(this::toOut)
				.collect(Collectors.toList());
	}

	private RuntimeException createLanguageNotFoundException(Long id) {
		return new RuntimeException("Language with id [" + id + "] does not exist");
	}

}
