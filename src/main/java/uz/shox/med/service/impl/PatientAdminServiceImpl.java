package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.patient.PatientFilterRequest;
import uz.shox.med.dto.patient.PatientResponse;
import uz.shox.med.entity.Patient;
import uz.shox.med.mapper.PatientMapper;
import uz.shox.med.repository.PatientRepository;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.PatientAdminService;
import uz.shox.med.specification.PatientSpecification;

@Service
@RequiredArgsConstructor
public class PatientAdminServiceImpl implements PatientAdminService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PageResponse<PatientResponse> getPatients(
            PatientFilterRequest filter,
            Pageable pageable
    ) {
        Page<PatientResponse> page = patientRepository
                .findAll(PatientSpecification.filter(filter), pageable)
                .map(patientMapper::toResponse);

        return PageResponse.of(page);
    }
}