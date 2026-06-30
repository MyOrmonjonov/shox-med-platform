package uz.shox.med.dto.home;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeNotificationDto {

    private Long id;

    private String title;

    private String message;

    private String type;

    private LocalDateTime createdAt;
}