package uz.shox.med.service;

import uz.shox.med.dto.appointment.AppointmentCreateRequest;
import uz.shox.med.dto.appointment.AppointmentHistoryResponse;
import uz.shox.med.dto.appointment.AppointmentResponse;
import uz.shox.med.entity.User;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(User user, AppointmentCreateRequest request);

    List<AppointmentResponse> myAppointments(User user);

}