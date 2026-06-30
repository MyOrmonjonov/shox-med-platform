package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.*;
import uz.shox.med.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

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
}