package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.entity.Notification;
import uz.shox.med.entity.User;
import uz.shox.med.enums.NotificationChannel;
import uz.shox.med.enums.NotificationType;
import uz.shox.med.repository.NotificationRepository;
import uz.shox.med.service.NotificationService;
import uz.shox.med.service.TelegramNotificationService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final TelegramNotificationService telegramNotificationService;

    @Override
    public Notification createTelegramNotification(
            User user,
            NotificationType type,
            String title,
            String message
    ) {
        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .channel(NotificationChannel.TELEGRAM)
                .title(title)
                .message(message)
                .sent(false)
                .build();

        notification = notificationRepository.save(notification);

        send(notification);

        return notification;
    }

    @Override
    public void send(Notification notification) {
        try {
            if (notification.getChannel() == NotificationChannel.TELEGRAM) {
                telegramNotificationService.sendToUser(
                        notification.getUser(),
                        notification.getMessage()
                );
            }

            notification.setSent(true);
            notification.setSentAt(LocalDateTime.now());
            notification.setErrorMessage(null);

        } catch (Exception e) {
            notification.setSent(false);
            notification.setErrorMessage(e.getMessage());
        }

        notificationRepository.save(notification);
    }
}