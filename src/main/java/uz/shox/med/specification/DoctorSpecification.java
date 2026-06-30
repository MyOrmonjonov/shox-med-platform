package uz.shox.med.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import uz.shox.med.dto.doctor.DoctorFilterRequest;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.User;

public class DoctorSpecification {

    public static Specification<Doctor> filter(DoctorFilterRequest filter) {

        return (root, query, cb) -> {

            query.distinct(true);
            var predicate = cb.conjunction();

            if (filter.getBranchId() != null) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("branch").get("id"), filter.getBranchId())
                );
            }

            if (filter.getSpecializationId() != null) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("specializationEntity").get("id"), filter.getSpecializationId())
                );
            }

            if (filter.getActive() != null) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("active"), filter.getActive())
                );
            }

            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {

                String search = "%" + filter.getSearch().toLowerCase() + "%";

                Join<Doctor, User> userJoin = root.join("user", JoinType.LEFT);

                predicate = cb.and(
                        predicate,
                        cb.or(
                                cb.like(cb.lower(userJoin.get("firstName")), search),
                                cb.like(cb.lower(userJoin.get("lastName")), search),
                                cb.like(cb.lower(userJoin.get("middleName")), search),
                                cb.like(cb.lower(userJoin.get("phone")), search),
                                cb.like(cb.lower(userJoin.get("email")), search),
                                cb.like(cb.lower(root.get("specialization")), search)
                        )
                );
            }

            return predicate;
        };
    }
}