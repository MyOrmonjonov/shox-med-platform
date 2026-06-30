package uz.shox.med.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.*;
import uz.shox.med.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>,
        JpaSpecificationExecutor<Appointment> {

    List<Appointment> findByPatientOrderByAppointmentTimeDesc(Patient patient);

    List<Appointment> findByDoctorAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(
            Doctor doctor,
            LocalDateTime from,
            LocalDateTime to
    );

    List<Appointment> findByBranchAndAppointmentTimeBetweenOrderByAppointmentTimeAsc(
            Branch branch,
            LocalDateTime from,
            LocalDateTime to
    );

    List<Appointment> findByStatus(AppointmentStatus status);
    long countByAppointmentTimeBetween(LocalDateTime from, LocalDateTime to);

    long countByStatus(AppointmentStatus status);

    List<Appointment> findByAppointmentTimeBetweenOrderByAppointmentTimeAsc(
            LocalDateTime from,
            LocalDateTime to
    );
    List<Appointment> findTop10ByOrderByCreatedAtDesc();

    Page<Appointment> findAllByOrderByCreatedAtDesc(Pageable pageable);
}