package uz.shox.med.dto.patient;

import lombok.*;
import uz.shox.med.enums.Gender;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePatientProfileRequest {

    private String firstName;

    private String lastName;

    private String middleName;

    private String phone;

    private LocalDate birthDate;

    private Gender gender;

    private String address;

    private String emergencyContactName;

    private String emergencyContactPhone;

    private String bloodType;

    private String allergies;

    private String chronicDiseases;
}