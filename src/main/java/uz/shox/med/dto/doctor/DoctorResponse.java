package uz.shox.med.dto.doctor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {

    private Long id;

    private Long userId;

    private String fullName;

    private String photoUrl;

    private String specialization;

    private Integer experienceYears;

    private String education;

    private String certificates;

    private String bio;

    private Double consultationPrice;

    private String workingSchedule;

    private Long branchId;

    private String branchName;

    private Boolean active;
}