package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.AuditLog;
import uz.shox.med.entity.User;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUserOrderByCreatedAtDesc(User user);
    List<AuditLog> findByEntityNameAndEntityIdOrderByCreatedAtDesc(String entityName, Long entityId);
}