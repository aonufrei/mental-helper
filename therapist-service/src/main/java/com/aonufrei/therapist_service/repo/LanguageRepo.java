package com.aonufrei.therapist_service.repo;

import com.aonufrei.therapist_service.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LanguageRepo extends JpaRepository<Language, Long> {

	@Query("select l from Language l where l.id in :ids")
	List<Language> findAllWithIds(@Param("ids") List<Long> ids);
}
