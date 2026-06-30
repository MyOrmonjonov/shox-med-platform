package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.schedule.AvailableTimeResponse;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.DoctorSchedule;
import uz.shox.med.enums.SlotStatus;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.repository.DoctorRepository;
import uz.shox.med.repository.DoctorScheduleRepository;
import uz.shox.med.repository.DoctorTimeSlotRepository;
import uz.shox.med.service.ScheduleService;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorTimeSlotRepository doctorTimeSlotRepository;

    @Override
    public List<AvailableTimeResponse> getAvailableTimes(Long doctorId, LocalDate date) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Shifokor topilmadi"));

        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();

        return doctorTimeSlotRepository
                .findByDoctorAndStartTimeBetweenAndActiveTrueOrderByStartTimeAsc(
                        doctor,
                        from,
                        to
                )
                .stream()
                .map(slot -> AvailableTimeResponse.builder()
                        .startTime(slot.getStartTime())
                        .endTime(slot.getEndTime())
                        .available(
                                slot.getStatus() == SlotStatus.AVAILABLE
                                        && slot.getStartTime().isAfter(LocalDateTime.now())
                        )
                        .build()
                )
                .toList();
    }
}