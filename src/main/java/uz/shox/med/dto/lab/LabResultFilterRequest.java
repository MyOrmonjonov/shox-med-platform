package uz.shox.med.dto.lab;

import lombok.*;
import uz.shox.med.enums.LabResultStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabResultFilterRequest {

    private String search;

    private LabResultStatus status;

    private Long branchId;

    private LocalDate fromDate;

    private LocalDate toDate;
}