package uz.shox.med.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import uz.shox.med.dto.patient.PatientFilterRequest;
import uz.shox.med.entity.Patient;
import uz.shox.med.entity.User;

public class PatientSpecification {

    public static Specification<Patient> filter(PatientFilterRequest filter) {

        return (root, query, cb) -> {

            query.distinct(true);

            var predicate = cb.conjunction();

            if (filter.getBloodType() != null && !filter.getBloodType().isBlank()) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("bloodType"), filter.getBloodType())
                );
            }

            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {

                String search = "%" + filter.getSearch().toLowerCase() + "%";

                Join<Patient, User> userJoin = root.join("user", JoinType.LEFT);

                predicate = cb.and(
                        predicate,
                        cb.or(
                                cb.like(cb.lower(userJoin.get("firstName")), search),
                                cb.like(cb.lower(userJoin.get("lastName")), search),
                                cb.like(cb.lower(userJoin.get("middleName")), search),
                                cb.like(cb.lower(userJoin.get("phone")), search),
                                cb.like(cb.lower(userJoin.get("email")), search),
                                cb.like(cb.lower(root.get("medicalCardNumber")), search)
                        )
                );
            }

            return predicate;
        };
    }
}