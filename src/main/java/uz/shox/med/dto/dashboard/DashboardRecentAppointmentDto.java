package uz.shox.med.dto.dashboard;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardRecentAppointmentDto {

    private Long id;

    private String appointmentNumber;

    private String patientName;

    private String doctorName;

    private String branchName;

    private LocalDateTime appointmentTime;

    private String status;
}