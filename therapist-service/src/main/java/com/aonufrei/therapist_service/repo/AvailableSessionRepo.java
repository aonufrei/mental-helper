package com.aonufrei.therapist_service.repo;

import com.aonufrei.therapist_service.model.AvailableSession;
import com.aonufrei.therapist_service.model.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface AvailableSessionRepo extends JpaRepository<AvailableSession, Long> {

	void deleteByTherapistAndDayOfWeek(Therapist therapist, DayOfWeek dayOfWeek);

	List<AvailableSession> findByTherapist_IdAndDayOfWeek(Long id, DayOfWeek dayOfWeek);

	List<AvailableSession> findByTherapist_Id(Long id);

}
