package com.aonufrei.therapist_service.rest;

import com.aonufrei.dto.SearchTherapistDto;
import com.aonufrei.dto.SearchTherapistOutDto;
import com.aonufrei.therapist_service.service.TherapistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/search")
public class SearchRestController {

	private final TherapistService therapistService;

	public SearchRestController(TherapistService therapistService) {
		this.therapistService = therapistService;
	}

	@GetMapping("therapist")
	public List<SearchTherapistOutDto> findTherapists(SearchTherapistDto searchQuery) {
		therapistService.modifySearchQuery(searchQuery);
		return therapistService.search(searchQuery);
	}

}
