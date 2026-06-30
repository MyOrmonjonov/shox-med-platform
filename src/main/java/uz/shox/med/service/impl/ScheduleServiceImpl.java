package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.schedule.AvailableTimeResponse;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.DoctorSchedule;
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

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        List<DoctorSchedule> schedules =
                doctorScheduleRepository.findByDoctorAndDayOfWeekAndActiveTrue(doctor, dayOfWeek);

        List<AvailableTimeResponse> result = new ArrayList<>();

        for (DoctorSchedule schedule : schedules) {

            LocalDateTime start = LocalDateTime.of(date, schedule.getStartTime());
            LocalDateTime end = LocalDateTime.of(date, schedule.getEndTime());

            LocalDateTime current = start;

            while (current.plusMinutes(schedule.getSlotDurationMinutes()).isBefore(end)
                    || current.plusMinutes(schedule.getSlotDurationMinutes()).isEqual(end)) {

                LocalDateTime slotEnd = current.plusMinutes(schedule.getSlotDurationMinutes());

                boolean booked = doctorTimeSlotRepository.existsByDoctorAndStartTimeAndBookedTrue(
                        doctor,
                        current
                );

                result.add(
                        AvailableTimeResponse.builder()
                                .startTime(current)
                                .endTime(slotEnd)
                                .available(!booked && current.isAfter(LocalDateTime.now()))
                                .build()
                );

                current = slotEnd;
            }
        }

        return result;
    }
}