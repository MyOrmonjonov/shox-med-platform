package uz.shox.med.service;

import uz.shox.med.entity.Notification;
import uz.shox.med.entity.User;
import uz.shox.med.enums.NotificationType;

public interface NotificationService {

    Notification createTelegramNotification(
            User user,
            NotificationType type,
            String title,
            String message
    );

    void send(Notification notification);
}