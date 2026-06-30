package uz.shox.med.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "telegram_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelegramAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long telegramId;

    private Long chatId;

    private String username;

    private String firstName;

    private String lastName;

    private String languageCode;

    private LocalDateTime linkedAt;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}