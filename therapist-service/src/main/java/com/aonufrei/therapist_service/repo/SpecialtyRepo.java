package com.aonufrei.therapist_service.repo;

import com.aonufrei.therapist_service.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecialtyRepo extends JpaRepository<Specialty, Long> {

	@Query("select s from Specialty s where s.id in :ids")
	List<Specialty> findAllWithIds(@Param("ids") List<Long> ids);

}
