package uz.shox.med.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.shox.med.dto.branch.BranchFilterRequest;
import uz.shox.med.entity.Branch;

public class BranchSpecification {

    public static Specification<Branch> filter(BranchFilterRequest filter) {

        return (root, query, cb) -> {

            var predicate = cb.conjunction();

            if (filter.getActive() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("active"), filter.getActive()));
            }

            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
                String search = "%" + filter.getSearch().toLowerCase() + "%";

                predicate = cb.and(
                        predicate,
                        cb.or(
                                cb.like(cb.lower(root.get("name")), search),
                                cb.like(cb.lower(root.get("address")), search),
                                cb.like(cb.lower(root.get("phone")), search)
                        )
                );
            }

            return predicate;
        };
    }
}