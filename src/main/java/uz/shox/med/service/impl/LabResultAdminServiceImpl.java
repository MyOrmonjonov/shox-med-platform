package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.lab.LabResultFilterRequest;
import uz.shox.med.dto.lab.LabResultResponse;
import uz.shox.med.mapper.LabResultMapper;
import uz.shox.med.repository.LabResultRepository;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.LabResultAdminService;
import uz.shox.med.specification.LabResultSpecification;

@Service
@RequiredArgsConstructor
public class LabResultAdminServiceImpl implements LabResultAdminService {

    private final LabResultRepository labResultRepository;
    private final LabResultMapper labResultMapper;

    @Override
    public PageResponse<LabResultResponse> getLabResults(
            LabResultFilterRequest filter,
            Pageable pageable
    ) {

        Page<LabResultResponse> page = labResultRepository
                .findAll(LabResultSpecification.filter(filter), pageable)
                .map(labResultMapper::toResponse);

        return PageResponse.of(page);
    }
}