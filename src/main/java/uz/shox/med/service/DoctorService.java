package uz.shox.med.service;

import uz.shox.med.dto.doctor.DoctorResponse;

import java.util.List;

public interface DoctorService {

    List<DoctorResponse> getActiveDoctors();

    List<DoctorResponse> getDoctorsByBranch(Long branchId);

    List<DoctorResponse> searchBySpecialization(String specialization);

    DoctorResponse getById(Long id);
}