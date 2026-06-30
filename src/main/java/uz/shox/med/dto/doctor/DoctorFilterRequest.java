package uz.shox.med.dto.doctor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorFilterRequest {

    private String search;

    private Long branchId;

    private Long specializationId;

    private Boolean active;
}