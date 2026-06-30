package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.TelegramAccount;
import uz.shox.med.entity.User;

import java.util.Optional;

public interface TelegramAccountRepository extends JpaRepository<TelegramAccount, Long> {
    Optional<TelegramAccount> findByTelegramId(Long telegramId);
    Optional<TelegramAccount> findByChatId(Long chatId);
    Optional<TelegramAccount> findByUser(User user);

    boolean existsByTelegramId(Long telegramId);
}