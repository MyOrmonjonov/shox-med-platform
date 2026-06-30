package uz.shox.med.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.entity.TelegramAccount;
import uz.shox.med.entity.User;
import uz.shox.med.repository.TelegramAccountRepository;
import uz.shox.med.service.TelegramNotificationService;

@Service
@RequiredArgsConstructor
public class TelegramNotificationServiceImpl implements TelegramNotificationService {

    private final TelegramBot telegramBot;
    private final TelegramAccountRepository telegramAccountRepository;

    @Override
    public void sendToUser(User user, String message) {

        TelegramAccount account = telegramAccountRepository.findByUser(user)
                .orElse(null);

        if (account == null || account.getChatId() == null) {
            return;
        }

        telegramBot.execute(
                new SendMessage(account.getChatId(), message)
        );
    }
}