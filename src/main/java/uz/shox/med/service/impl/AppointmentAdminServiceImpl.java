package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.appointment.AppointmentFilterRequest;
import uz.shox.med.dto.appointment.AppointmentResponse;
import uz.shox.med.entity.Appointment;
import uz.shox.med.mapper.AppointmentMapper;
import uz.shox.med.repository.AppointmentRepository;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.AppointmentAdminService;
import uz.shox.med.specification.AppointmentSpecification;

@Service
@RequiredArgsConstructor
public class AppointmentAdminServiceImpl implements AppointmentAdminService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public PageResponse<AppointmentResponse> getAppointments(
            AppointmentFilterRequest filter,
            Pageable pageable
    ) {
        Page<AppointmentResponse> page = appointmentRepository
                .findAll(AppointmentSpecification.filter(filter), pageable)
                .map(appointmentMapper::toResponse);

        return PageResponse.of(page);
    }
}