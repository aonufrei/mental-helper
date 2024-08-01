package com.aonufrei.therapist_service.repo;

import com.aonufrei.dto.SearchTherapistOutDto;
import com.aonufrei.therapist_service.model.Therapist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TherapistRepo extends JpaRepository<Therapist, Long> {

	@Query("SELECT DISTINCT t FROM Therapist t " +
			"JOIN t.languages l " +
			"JOIN t.specialties s " +
			"WHERE l.id IN :languages " +
			"AND s.id IN :specialties")
	List<Therapist> search(@Param("languages") List<Long> languages,
	                       @Param("specialties") List<Long> specialties,
	                       Pageable pageable);

}
