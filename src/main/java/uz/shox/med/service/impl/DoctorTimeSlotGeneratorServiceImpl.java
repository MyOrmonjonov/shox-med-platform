package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.DoctorSchedule;
import uz.shox.med.entity.DoctorTimeSlot;
import uz.shox.med.enums.SlotStatus;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.repository.DoctorRepository;
import uz.shox.med.repository.DoctorScheduleRepository;
import uz.shox.med.repository.DoctorTimeSlotRepository;
import uz.shox.med.service.DoctorTimeSlotGeneratorService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorTimeSlotGeneratorServiceImpl implements DoctorTimeSlotGeneratorService {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorTimeSlotRepository doctorTimeSlotRepository;

    @Override
    @Transactional
    public void generateSlotsForDoctor(Long doctorId, LocalDate fromDate, LocalDate toDate) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Shifokor topilmadi"));

        LocalDate currentDate = fromDate;

        while (!currentDate.isAfter(toDate)) {

            List<DoctorSchedule> schedules =
                    doctorScheduleRepository.findByDoctorAndDayOfWeekAndActiveTrue(
                            doctor,
                            currentDate.getDayOfWeek()
                    );

            for (DoctorSchedule schedule : schedules) {
                generateSlotsForSchedule(doctor, schedule, currentDate);
            }

            currentDate = currentDate.plusDays(1);
        }
    }

    @Override
    @Transactional
    public void generateSlotsForAllDoctors(LocalDate fromDate, LocalDate toDate) {

        List<Doctor> doctors = doctorRepository.findByActiveTrue();

        for (Doctor doctor : doctors) {
            generateSlotsForDoctor(doctor.getId(), fromDate, toDate);
        }
    }

    private void generateSlotsForSchedule(
            Doctor doctor,
            DoctorSchedule schedule,
            LocalDate date
    ) {

        LocalTime current = schedule.getStartTime();
        LocalTime end = schedule.getEndTime();

        while (!current.plusMinutes(schedule.getSlotDurationMinutes()).isAfter(end)) {

            LocalDateTime startDateTime = LocalDateTime.of(date, current);
            LocalDateTime endDateTime = startDateTime.plusMinutes(schedule.getSlotDurationMinutes());

            boolean exists = doctorTimeSlotRepository
                    .findByDoctorAndStartTime(doctor, startDateTime)
                    .isPresent();

            if (!exists) {
                DoctorTimeSlot slot = DoctorTimeSlot.builder()
                        .doctor(doctor)
                        .branch(schedule.getBranch())
                        .startTime(startDateTime)
                        .endTime(endDateTime)
                        .status(SlotStatus.AVAILABLE)
                        .active(true)
                        .build();

                doctorTimeSlotRepository.save(slot);
            }

            current = current.plusMinutes(schedule.getSlotDurationMinutes());
        }
    }
}