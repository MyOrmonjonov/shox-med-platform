package uz.shox.med.dto.dashboard;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardProfileDto {

    private Long userId;

    private String fullName;

    private String photoUrl;

    private String medicalCardNumber;

    private Boolean profileVerified;
}