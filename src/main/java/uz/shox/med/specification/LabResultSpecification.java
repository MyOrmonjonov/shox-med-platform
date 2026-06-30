package uz.shox.med.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import uz.shox.med.dto.lab.LabResultFilterRequest;
import uz.shox.med.entity.LabResult;
import uz.shox.med.entity.Patient;
import uz.shox.med.entity.User;

import java.time.LocalDateTime;

public class LabResultSpecification {

    public static Specification<LabResult> filter(LabResultFilterRequest filter) {

        return (root, query, cb) -> {

            query.distinct(true);

            var predicate = cb.conjunction();

            if (filter.getStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), filter.getStatus()));
            }

            if (filter.getBranchId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("branch").get("id"), filter.getBranchId()));
            }

            if (filter.getFromDate() != null) {
                LocalDateTime from = filter.getFromDate().atStartOfDay();
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("resultDate"), from));
            }

            if (filter.getToDate() != null) {
                LocalDateTime to = filter.getToDate().plusDays(1).atStartOfDay();
                predicate = cb.and(predicate, cb.lessThan(root.get("resultDate"), to));
            }

            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
                String search = "%" + filter.getSearch().toLowerCase() + "%";

                Join<LabResult, Patient> patientJoin = root.join("patient", JoinType.LEFT);
                Join<Patient, User> userJoin = patientJoin.join("user", JoinType.LEFT);

                predicate = cb.and(
                        predicate,
                        cb.or(
                                cb.like(cb.lower(root.get("testName")), search),
                                cb.like(cb.lower(root.get("testType")), search),
                                cb.like(cb.lower(userJoin.get("firstName")), search),
                                cb.like(cb.lower(userJoin.get("lastName")), search),
                                cb.like(cb.lower(userJoin.get("phone")), search)
                        )
                );
            }

            return predicate;
        };
    }
}