package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.auth.UserResponse;
import uz.shox.med.entity.Patient;
import uz.shox.med.entity.User;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.mapper.UserMapper;
import uz.shox.med.repository.PatientRepository;
import uz.shox.med.service.PatientService;
import uz.shox.med.dto.patient.UpdatePatientProfileRequest;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserMapper userMapper;

    @Override
    public Patient getByUser(User user) {
        return patientRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Bemor profili topilmadi"));
    }

    @Override
    public UserResponse getMyProfile(User user) {
        getByUser(user);
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateMyProfile(User user, UpdatePatientProfileRequest request) {

        Patient patient = getByUser(user);

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getMiddleName() != null) user.setMiddleName(request.getMiddleName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getBirthDate() != null) user.setBirthDate(request.getBirthDate());
        if (request.getGender() != null) user.setGender(request.getGender());

        if (request.getAddress() != null) patient.setAddress(request.getAddress());
        if (request.getEmergencyContactName() != null) patient.setEmergencyContactName(request.getEmergencyContactName());
        if (request.getEmergencyContactPhone() != null) patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        if (request.getBloodType() != null) patient.setBloodType(request.getBloodType());
        if (request.getAllergies() != null) patient.setAllergies(request.getAllergies());
        if (request.getChronicDiseases() != null) patient.setChronicDiseases(request.getChronicDiseases());

        patientRepository.save(patient);

        return userMapper.toResponse(user);
    }

}