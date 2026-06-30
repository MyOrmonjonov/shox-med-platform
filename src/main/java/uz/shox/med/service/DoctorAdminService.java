package uz.shox.med.service;

import org.springframework.data.domain.Pageable;
import uz.shox.med.dto.doctor.DoctorFilterRequest;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.response.PageResponse;

public interface DoctorAdminService {

    PageResponse<DoctorResponse> getDoctors(
            DoctorFilterRequest filter,
            Pageable pageable
    );
}