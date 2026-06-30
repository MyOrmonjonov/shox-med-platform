package uz.shox.med.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import uz.shox.med.dto.appointment.AppointmentFilterRequest;
import uz.shox.med.entity.*;
import uz.shox.med.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentSpecification {

    public static Specification<Appointment> filter(AppointmentFilterRequest filter) {

        return (root, query, cb) -> {

            query.distinct(true);

            var predicate = cb.conjunction();

            if (filter.getFromDate() != null) {
                LocalDateTime from = filter.getFromDate().atStartOfDay();
                predicate = cb.and(
                        predicate,
                        cb.greaterThanOrEqualTo(root.get("appointmentTime"), from)
                );
            }

            if (filter.getToDate() != null) {
                LocalDateTime to = filter.getToDate().plusDays(1).atStartOfDay();
                predicate = cb.and(
                        predicate,
                        cb.lessThan(root.get("appointmentTime"), to)
                );
            }

            AppointmentStatus status = filter.getStatus();
            if (status != null) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("status"), status)
                );
            }

            if (filter.getDoctorId() != null) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("doctor").get("id"), filter.getDoctorId())
                );
            }

            if (filter.getBranchId() != null) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("branch").get("id"), filter.getBranchId())
                );
            }

            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {

                String search = "%" + filter.getSearch().toLowerCase() + "%";

                Join<Appointment, Patient> patientJoin = root.join("patient", JoinType.LEFT);
                Join<Patient, User> patientUserJoin = patientJoin.join("user", JoinType.LEFT);

                predicate = cb.and(
                        predicate,
                        cb.or(
                                cb.like(cb.lower(patientUserJoin.get("firstName")), search),
                                cb.like(cb.lower(patientUserJoin.get("lastName")), search),
                                cb.like(cb.lower(patientUserJoin.get("middleName")), search),
                                cb.like(cb.lower(patientUserJoin.get("phone")), search),
                                cb.like(cb.lower(patientUserJoin.get("email")), search),
                                cb.like(cb.lower(root.get("appointmentNumber")), search),
                                cb.like(cb.lower(root.get("queueNumber")), search)
                        )
                );
            }

            return predicate;
        };
    }
}