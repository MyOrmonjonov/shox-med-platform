package uz.shox.med.service;

import uz.shox.med.dto.appointment.AppointmentHistoryResponse;
import uz.shox.med.entity.Appointment;
import uz.shox.med.entity.User;
import uz.shox.med.enums.AppointmentAction;
import uz.shox.med.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentHistoryService {

    void saveHistory(
            Appointment appointment,
            AppointmentAction action,
            AppointmentStatus oldStatus,
            AppointmentStatus newStatus,
            User changedBy,
            String reason,
            String comment
    );
    List<AppointmentHistoryResponse> getHistory(Long appointmentId);
}