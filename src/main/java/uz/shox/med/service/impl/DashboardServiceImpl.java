package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.dashboard.DashboardChartPointDto;
import uz.shox.med.dto.dashboard.DashboardResponse;
import uz.shox.med.dto.dashboard.DashboardTodayAppointmentDto;
import uz.shox.med.entity.Appointment;
import uz.shox.med.enums.AppointmentStatus;
import uz.shox.med.repository.*;
import uz.shox.med.service.DashboardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import uz.shox.med.dto.dashboard.DashboardRecentAppointmentDto;
import uz.shox.med.response.PageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final BranchRepository branchRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public DashboardResponse getDashboard() {

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime tomorrowStart = LocalDate.now().plusDays(1).atStartOfDay();

        List<DashboardTodayAppointmentDto> todayList =
                appointmentRepository
                        .findByAppointmentTimeBetweenOrderByAppointmentTimeAsc(
                                todayStart,
                                tomorrowStart
                        )
                        .stream()
                        .map(appointment -> DashboardTodayAppointmentDto.builder()
                                .id(appointment.getId())
                                .appointmentNumber(appointment.getAppointmentNumber())
                                .queueNumber(appointment.getQueueNumber())
                                .appointmentTime(appointment.getAppointmentTime())
                                .patientName(
                                        appointment.getPatient() != null &&
                                                appointment.getPatient().getUser() != null
                                                ? appointment.getPatient().getUser().getFirstName() + " " +
                                                appointment.getPatient().getUser().getLastName()
                                                : null
                                )
                                .doctorName(
                                        appointment.getDoctor() != null &&
                                                appointment.getDoctor().getUser() != null
                                                ? appointment.getDoctor().getUser().getFirstName() + " " +
                                                appointment.getDoctor().getUser().getLastName()
                                                : null
                                )
                                .branchName(
                                        appointment.getBranch() != null
                                                ? appointment.getBranch().getName()
                                                : null
                                )
                                .status(
                                        appointment.getStatus() != null
                                                ? appointment.getStatus().name()
                                                : null
                                )
                                .build()
                        )
                        .toList();

        return DashboardResponse.builder()
                .totalPatients(patientRepository.count())
                .totalDoctors(doctorRepository.count())
                .totalBranches(branchRepository.count())
                .totalAppointments(appointmentRepository.count())
                .todayAppointments(
                        appointmentRepository.countByAppointmentTimeBetween(todayStart, tomorrowStart)
                )
                .confirmedAppointments(
                        appointmentRepository.countByStatus(AppointmentStatus.CONFIRMED)
                )
                .waitingAppointments(
                        appointmentRepository.countByStatus(AppointmentStatus.CREATED)
                )
                .inProgressAppointments(
                        appointmentRepository.countByStatus(AppointmentStatus.IN_PROGRESS)
                )
                .completedAppointments(
                        appointmentRepository.countByStatus(AppointmentStatus.COMPLETED)
                )
                .cancelledAppointments(
                        appointmentRepository.countByStatus(AppointmentStatus.CANCELLED)
                )
                .todayAppointmentsList(todayList)
                .build();
    }

    @Override
    public PageResponse<DashboardRecentAppointmentDto> getRecentAppointments(Pageable pageable) {

        Page<Appointment> page = appointmentRepository
                .findAllByOrderByCreatedAtDesc(pageable);

        Page<DashboardRecentAppointmentDto> dtoPage = page.map(appointment ->
                DashboardRecentAppointmentDto.builder()
                        .id(appointment.getId())
                        .appointmentNumber(appointment.getAppointmentNumber())
                        .patientName(
                                appointment.getPatient().getUser().getFirstName()
                                        + " "
                                        + appointment.getPatient().getUser().getLastName()
                        )
                        .doctorName(
                                appointment.getDoctor().getUser().getFirstName()
                                        + " "
                                        + appointment.getDoctor().getUser().getLastName()
                        )
                        .branchName(
                                appointment.getBranch().getName()
                        )
                        .appointmentTime(
                                appointment.getAppointmentTime()
                        )
                        .status(
                                appointment.getStatus().name()
                        )
                        .build()
        );

        return PageResponse.of(dtoPage);
    }


    @Override
    public List<DashboardChartPointDto> getWeeklyAppointments() {

        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(6);

        return start.datesUntil(today.plusDays(1))
                .map(date -> {
                    LocalDateTime from = date.atStartOfDay();
                    LocalDateTime to = date.plusDays(1).atStartOfDay();

                    long count = appointmentRepository.countByAppointmentTimeBetween(from, to);

                    return DashboardChartPointDto.builder()
                            .label(date.toString())
                            .value(count)
                            .build();
                })
                .toList();
    }
}