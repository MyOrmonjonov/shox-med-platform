package uz.shox.med.dto.patient;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientFilterRequest {

    private String search;

    private String bloodType;
}