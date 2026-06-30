package uz.shox.med.mapper;

import org.springframework.stereotype.Component;
import uz.shox.med.dto.serviceitem.ServiceItemResponse;
import uz.shox.med.entity.ServiceItem;

@Component
public class ServiceItemMapper {

    public ServiceItemResponse toResponse(ServiceItem serviceItem) {

        if (serviceItem == null) {
            return null;
        }

        return ServiceItemResponse.builder()
                .id(serviceItem.getId())
                .name(serviceItem.getName())
                .category(serviceItem.getCategory())
                .description(serviceItem.getDescription())
                .price(serviceItem.getPrice())
                .branchId(serviceItem.getBranch() != null ? serviceItem.getBranch().getId() : null)
                .branchName(serviceItem.getBranch() != null ? serviceItem.getBranch().getName() : null)
                .active(serviceItem.getActive())
                .build();
    }
}