package uz.shox.med.service;

import uz.shox.med.dto.appointment.AppointmentResponse;
import uz.shox.med.entity.User;

public interface AppointmentWorkflowService {

    AppointmentResponse confirm(Long appointmentId, User user);

    AppointmentResponse cancel(Long appointmentId, User user, String reason);

    AppointmentResponse checkIn(Long appointmentId, User user);

    AppointmentResponse start(Long appointmentId, User user);

    AppointmentResponse complete(Long appointmentId, User user);

    AppointmentResponse noShow(Long appointmentId, User user);
}