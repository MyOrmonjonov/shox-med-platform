package uz.shox.med.dto.dashboard;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardAppointmentDto {

    private Long appointmentId;

    private LocalDateTime appointmentTime;

    private String doctorName;

    private String specialization;

    private String branchName;

    private String status;
}