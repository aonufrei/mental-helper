package com.aonufrei.appointment_service.repo;

import com.aonufrei.appointment_service.enums.AppointmentState;
import com.aonufrei.appointment_service.model.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {


	@Query("SELECT a FROM Appointment a WHERE a.therapistId = :therapistId AND a.time > :time AND a.state = :state")
	List<Appointment> findAllOfTherapist(@Param("therapistId") Long therapistId,
	                                     @Param("state") AppointmentState state,
	                                     @Param("time") LocalDateTime time,
	                                     Pageable page);

	@Query("SELECT a FROM Appointment a WHERE a.patientId = :patientId AND a.time > :time AND a.state = :state")
	List<Appointment> findAllOfPatient(@Param("patientId") Long patientId,
	                                   @Param("state") AppointmentState state,
	                                   @Param("time") LocalDateTime time,
	                                   Pageable page);

}
