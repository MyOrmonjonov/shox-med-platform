package uz.shox.med.service;

import uz.shox.med.dto.auth.UserResponse;
import uz.shox.med.dto.patient.UpdatePatientProfileRequest;
import uz.shox.med.entity.Patient;
import uz.shox.med.entity.User;

public interface PatientService {

    Patient getByUser(User user);

    UserResponse getMyProfile(User user);
    UserResponse updateMyProfile(User user, UpdatePatientProfileRequest request);
}