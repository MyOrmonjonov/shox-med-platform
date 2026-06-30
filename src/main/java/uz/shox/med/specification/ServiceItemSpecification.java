package uz.shox.med.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.shox.med.dto.serviceitem.ServiceItemFilterRequest;
import uz.shox.med.entity.ServiceItem;

public class ServiceItemSpecification {

    public static Specification<ServiceItem> filter(ServiceItemFilterRequest filter) {

        return (root, query, cb) -> {

            var predicate = cb.conjunction();

            if (filter.getActive() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("active"), filter.getActive()));
            }

            if (filter.getBranchId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("branch").get("id"), filter.getBranchId()));
            }

            if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
                predicate = cb.and(
                        predicate,
                        cb.like(cb.lower(root.get("category")), "%" + filter.getCategory().toLowerCase() + "%")
                );
            }

            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
                String search = "%" + filter.getSearch().toLowerCase() + "%";

                predicate = cb.and(
                        predicate,
                        cb.or(
                                cb.like(cb.lower(root.get("name")), search),
                                cb.like(cb.lower(root.get("description")), search),
                                cb.like(cb.lower(root.get("category")), search)
                        )
                );
            }

            return predicate;
        };
    }
}