package uz.shox.med.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.shox.med.enums.NotificationChannel;
import uz.shox.med.enums.NotificationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Boolean sent = false;

    private LocalDateTime sentAt;

    private String errorMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}