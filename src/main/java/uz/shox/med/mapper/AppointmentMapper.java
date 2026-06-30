package uz.shox.med.mapper;

import org.springframework.stereotype.Component;
import uz.shox.med.dto.appointment.AppointmentResponse;
import uz.shox.med.entity.Appointment;

@Component
public class AppointmentMapper {

    public AppointmentResponse toResponse(Appointment appointment) {

        if (appointment == null) {
            return null;
        }

        String patientName = null;
        if (appointment.getPatient() != null && appointment.getPatient().getUser() != null) {
            patientName = appointment.getPatient().getUser().getFirstName() + " " +
                    appointment.getPatient().getUser().getLastName();
        }

        String doctorName = null;
        String specialization = null;

        if (appointment.getDoctor() != null && appointment.getDoctor().getUser() != null) {
            doctorName = appointment.getDoctor().getUser().getFirstName() + " " +
                    appointment.getDoctor().getUser().getLastName();

            specialization = appointment.getDoctor().getSpecializationEntity() != null
                    ? appointment.getDoctor().getSpecializationEntity().getNameUz()
                    : null;
        }

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .queueNumber(appointment.getQueueNumber())
                .status(appointment.getStatus() != null ? appointment.getStatus().name() : null)
                .appointmentTime(appointment.getAppointmentTime())
                .patientName(patientName)
                .doctorName(doctorName)
                .specialization(specialization)
                .serviceName(appointment.getServiceItem() != null ? appointment.getServiceItem().getName() : null)
                .servicePrice(appointment.getServiceItem() != null ? appointment.getServiceItem().getPrice() : null)
                .branchName(appointment.getBranch() != null ? appointment.getBranch().getName() : null)
                .patientComment(appointment.getPatientComment())
                .adminComment(appointment.getAdminComment())
                .createdBy(appointment.getCreatedBy() != null
                        ? appointment.getCreatedBy().getFirstName() + " " + appointment.getCreatedBy().getLastName()
                        : null)
                .createdAt(appointment.getCreatedAt())
                .build();
    }
}