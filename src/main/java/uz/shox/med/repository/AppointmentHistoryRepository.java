package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.Appointment;
import uz.shox.med.entity.AppointmentHistory;

import java.util.List;

public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistory, Long> {

    List<AppointmentHistory> findByAppointmentOrderByActionTimeAsc(Appointment appointment);
}