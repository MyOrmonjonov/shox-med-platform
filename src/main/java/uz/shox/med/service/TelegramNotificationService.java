package uz.shox.med.service;

import uz.shox.med.entity.User;

public interface TelegramNotificationService {

    void sendToUser(User user, String message);
}