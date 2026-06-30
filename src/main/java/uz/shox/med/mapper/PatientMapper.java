package uz.shox.med.mapper;

import org.springframework.stereotype.Component;
import uz.shox.med.dto.patient.PatientResponse;
import uz.shox.med.entity.Patient;
import uz.shox.med.entity.User;

@Component
public class PatientMapper {

    public PatientResponse toResponse(Patient patient) {

        User user = patient.getUser();

        return PatientResponse.builder()
                .id(patient.getId())
                .userId(user != null ? user.getId() : null)
                .fullName(user != null ? user.getFirstName() + " " + user.getLastName() : null)
                .phone(user != null ? user.getPhone() : null)
                .email(user != null ? user.getEmail() : null)
                .medicalCardNumber(patient.getMedicalCardNumber())
                .address(patient.getAddress())
                .bloodType(patient.getBloodType())
                .build();
    }
}