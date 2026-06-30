package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.appointment.AppointmentHistoryResponse;
import uz.shox.med.entity.Appointment;
import uz.shox.med.entity.AppointmentHistory;
import uz.shox.med.entity.User;
import uz.shox.med.enums.AppointmentAction;
import uz.shox.med.enums.AppointmentStatus;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.mapper.AppointmentHistoryMapper;
import uz.shox.med.repository.AppointmentHistoryRepository;
import uz.shox.med.repository.AppointmentRepository;
import uz.shox.med.service.AppointmentHistoryService;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AppointmentHistoryServiceImpl implements AppointmentHistoryService {

    private final AppointmentHistoryRepository historyRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentHistoryMapper historyMapper;

    @Override
    public void saveHistory(
            Appointment appointment,
            AppointmentAction action,
            AppointmentStatus oldStatus,
            AppointmentStatus newStatus,
            User changedBy,
            String reason,
            String comment
    ) {
        AppointmentHistory history = AppointmentHistory.builder()
                .appointment(appointment)
                .action(action)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .changedBy(changedBy)
                .reason(reason)
                .comment(comment)
                .actionTime(LocalDateTime.now())
                .build();

        historyRepository.save(history);

    }
    @Override
    public List<AppointmentHistoryResponse> getHistory(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Qabul topilmadi"));

        return historyRepository
                .findByAppointmentOrderByActionTimeAsc(appointment)
                .stream()
                .map(historyMapper::toResponse)
                .toList();
    }
}