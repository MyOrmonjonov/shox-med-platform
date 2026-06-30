package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.doctor.DoctorFilterRequest;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.entity.Doctor;
import uz.shox.med.mapper.DoctorMapper;
import uz.shox.med.repository.DoctorRepository;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.DoctorAdminService;
import uz.shox.med.specification.DoctorSpecification;

@Service
@RequiredArgsConstructor
public class DoctorAdminServiceImpl implements DoctorAdminService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public PageResponse<DoctorResponse> getDoctors(
            DoctorFilterRequest filter,
            Pageable pageable
    ) {
        Page<DoctorResponse> page = doctorRepository
                .findAll(DoctorSpecification.filter(filter), pageable)
                .map(doctorMapper::toResponse);

        return PageResponse.of(page);
    }
}