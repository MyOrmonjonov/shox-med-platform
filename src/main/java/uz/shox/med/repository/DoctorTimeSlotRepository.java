package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.DoctorTimeSlot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DoctorTimeSlotRepository extends JpaRepository<DoctorTimeSlot, Long> {

    List<DoctorTimeSlot> findByDoctorAndStartTimeBetweenAndActiveTrueOrderByStartTimeAsc(
            Doctor doctor,
            LocalDateTime from,
            LocalDateTime to
    );

    boolean existsByDoctorAndStartTimeAndBookedTrue(
            Doctor doctor,
            LocalDateTime startTime
    );


    Optional<DoctorTimeSlot> findByDoctorAndStartTime(
            Doctor doctor,
            LocalDateTime startTime
    );
}