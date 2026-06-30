package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.Notification;
import uz.shox.med.entity.User;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    List<Notification> findBySentFalse();
}