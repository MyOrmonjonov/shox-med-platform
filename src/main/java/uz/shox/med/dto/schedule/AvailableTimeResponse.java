package uz.shox.med.dto.schedule;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableTimeResponse {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean available;
}