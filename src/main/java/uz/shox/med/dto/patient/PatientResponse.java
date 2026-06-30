package uz.shox.med.dto.patient;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponse {

    private Long id;

    private Long userId;

    private String fullName;

    private String phone;

    private String email;

    private String medicalCardNumber;

    private String address;

    private String bloodType;
}