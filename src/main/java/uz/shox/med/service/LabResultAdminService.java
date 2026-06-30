package uz.shox.med.service;

import org.springframework.data.domain.Pageable;
import uz.shox.med.dto.lab.LabResultFilterRequest;
import uz.shox.med.dto.lab.LabResultResponse;
import uz.shox.med.response.PageResponse;

public interface LabResultAdminService {

    PageResponse<LabResultResponse> getLabResults(
            LabResultFilterRequest filter,
            Pageable pageable
    );
}