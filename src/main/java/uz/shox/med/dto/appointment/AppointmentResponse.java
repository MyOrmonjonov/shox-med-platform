package uz.shox.med.dto.appointment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private Long id;

    private String queueNumber;

    private String status;

    private LocalDateTime appointmentTime;

    private String patientName;

    private String doctorName;

    private String specialization;

    private String serviceName;

    private Double servicePrice;

    private String branchName;

    private String patientComment;

    private String adminComment;

    private String createdBy;

    private LocalDateTime createdAt;
}