package uz.shox.med.service;

import org.springframework.data.domain.Pageable;
import uz.shox.med.dto.patient.PatientFilterRequest;
import uz.shox.med.dto.patient.PatientResponse;
import uz.shox.med.response.PageResponse;

public interface PatientAdminService {

    PageResponse<PatientResponse> getPatients(
            PatientFilterRequest filter,
            Pageable pageable
    );
}