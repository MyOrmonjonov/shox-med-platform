package uz.shox.med.service;

import org.springframework.data.domain.Pageable;
import uz.shox.med.dto.appointment.AppointmentFilterRequest;
import uz.shox.med.dto.appointment.AppointmentResponse;
import uz.shox.med.response.PageResponse;

public interface AppointmentAdminService {

    PageResponse<AppointmentResponse> getAppointments(
            AppointmentFilterRequest filter,
            Pageable pageable
    );
}