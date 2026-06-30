package uz.shox.med.service;

import org.springframework.data.domain.Pageable;
import uz.shox.med.dto.serviceitem.ServiceItemFilterRequest;
import uz.shox.med.dto.serviceitem.ServiceItemResponse;
import uz.shox.med.response.PageResponse;

public interface ServiceItemAdminService {

    PageResponse<ServiceItemResponse> getServices(
            ServiceItemFilterRequest filter,
            Pageable pageable
    );
}