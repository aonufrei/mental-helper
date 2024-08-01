package com.aonufrei.therapist_service.service;

import com.aonufrei.dto.SpecialtyInDto;
import com.aonufrei.dto.SpecialtyOutDto;
import com.aonufrei.therapist_service.model.Specialty;
import com.aonufrei.therapist_service.repo.SpecialtyRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialtyService {

	private final ModelMapper modelMapper;
	private final SpecialtyRepo specialtyRepo;

	public SpecialtyService(ModelMapper modelMapper, SpecialtyRepo specialtyRepo) {
		this.modelMapper = modelMapper;
		this.specialtyRepo = specialtyRepo;
	}

	public List<SpecialtyOutDto> getAll() {
		return specialtyRepo.findAll().stream()
				.map(this::toOut).collect(Collectors.toList());
	}

	public List<Specialty> findAllWithIds(List<Long> ids) {
		return specialtyRepo.findAllWithIds(ids);
	}

	public Optional<Specialty> findById(Long id) {
		return specialtyRepo.findById(id);
	}

	public SpecialtyOutDto create(SpecialtyInDto specialtyInDto) {
		var created = specialtyRepo.save(toModel(specialtyInDto));
		return toOut(created);
	}

	public SpecialtyOutDto update(Long id, SpecialtyInDto specialtyInDto) {
		if (!specialtyRepo.existsById(id)) {
			throw createSpecialtyNotFoundException(id);
		}
		var model = toModel(specialtyInDto);
		model.setId(id);
		var created = specialtyRepo.save(model);
		return toOut(created);
	}

	public void delete(Long id) {
		var toDelete = findById(id).orElseThrow(() -> createSpecialtyNotFoundException(id));
		specialtyRepo.delete(toDelete);
	}


	public Specialty toModel(SpecialtyInDto specialtyInDto) {
		return modelMapper.map(specialtyInDto, Specialty.class);
	}


	public SpecialtyOutDto toOut(Specialty specialty) {
		return modelMapper.map(specialty, SpecialtyOutDto.class);
	}

	public List<SpecialtyOutDto> toSpecialtyOutList(List<Specialty> specialties) {
		if (specialties == null) return Collections.emptyList();
		return specialties.stream()
				.map(this::toOut)
				.collect(Collectors.toList());
	}

	private RuntimeException createSpecialtyNotFoundException(Long id) {
		return new RuntimeException("Specialty with id [" + id + "] does not exist");
	}

}
