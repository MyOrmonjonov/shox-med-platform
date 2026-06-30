package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.shox.med.entity.Branch;
import uz.shox.med.entity.ServiceItem;

import java.util.List;

public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long>,
        JpaSpecificationExecutor<ServiceItem> {
    List<ServiceItem> findByActiveTrue();
    List<ServiceItem> findByBranchAndActiveTrue(Branch branch);
    List<ServiceItem> findByCategoryContainingIgnoreCaseAndActiveTrue(String category);
}