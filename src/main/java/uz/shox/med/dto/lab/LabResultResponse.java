package uz.shox.med.dto.lab;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabResultResponse {

    private Long id;

    private String testName;

    private String testType;

    private String status;

    private String patientName;

    private String branchName;

    private String pdfUrl;

    private LocalDateTime resultDate;
}