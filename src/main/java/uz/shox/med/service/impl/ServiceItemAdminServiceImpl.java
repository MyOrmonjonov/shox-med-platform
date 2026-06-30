package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.serviceitem.ServiceItemFilterRequest;
import uz.shox.med.dto.serviceitem.ServiceItemResponse;
import uz.shox.med.mapper.ServiceItemMapper;
import uz.shox.med.repository.ServiceItemRepository;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.ServiceItemAdminService;
import uz.shox.med.specification.ServiceItemSpecification;

@Service
@RequiredArgsConstructor
public class ServiceItemAdminServiceImpl implements ServiceItemAdminService {

    private final ServiceItemRepository serviceItemRepository;
    private final ServiceItemMapper serviceItemMapper;

    @Override
    public PageResponse<ServiceItemResponse> getServices(
            ServiceItemFilterRequest filter,
            Pageable pageable
    ) {
        Page<ServiceItemResponse> page = serviceItemRepository
                .findAll(ServiceItemSpecification.filter(filter), pageable)
                .map(serviceItemMapper::toResponse);

        return PageResponse.of(page);
    }
}