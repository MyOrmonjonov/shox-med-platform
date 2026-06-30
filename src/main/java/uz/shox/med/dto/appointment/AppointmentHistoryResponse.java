package uz.shox.med.dto.appointment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentHistoryResponse {

    private Long id;

    private String action;

    private String oldStatus;

    private String newStatus;

    private String changedBy;

    private String reason;

    private String comment;

    private LocalDateTime actionTime;
}