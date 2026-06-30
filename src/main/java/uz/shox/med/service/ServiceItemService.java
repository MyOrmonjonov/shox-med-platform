package uz.shox.med.service;

import uz.shox.med.dto.serviceitem.ServiceItemResponse;

import java.util.List;

public interface ServiceItemService {

    List<ServiceItemResponse> getActiveServices();

    List<ServiceItemResponse> getServicesByBranch(Long branchId);

    List<ServiceItemResponse> searchByCategory(String category);

    ServiceItemResponse getById(Long id);
}