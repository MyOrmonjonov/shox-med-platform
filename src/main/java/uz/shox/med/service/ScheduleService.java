package uz.shox.med.service;

import uz.shox.med.dto.schedule.AvailableTimeResponse;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    List<AvailableTimeResponse> getAvailableTimes(Long doctorId, LocalDate date);
}