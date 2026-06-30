package uz.shox.med.dto.appointment;

import lombok.*;
import uz.shox.med.enums.AppointmentStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentFilterRequest {

    private LocalDate fromDate;

    private LocalDate toDate;

    private AppointmentStatus status;

    private Long doctorId;

    private Long branchId;

    private String search;
}