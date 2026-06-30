package uz.shox.med.dto.dashboard;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardLabResultDto {

    private Long labResultId;

    private String testName;

    private String status;

    private String pdfUrl;

    private LocalDateTime resultDate;
}