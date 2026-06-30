package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.DoctorSchedule;

import java.time.DayOfWeek;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctorAndDayOfWeekAndActiveTrue(
            Doctor doctor,
            DayOfWeek dayOfWeek
    );

    List<DoctorSchedule> findByDoctorAndActiveTrue(Doctor doctor);
}