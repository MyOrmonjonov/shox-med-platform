package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.serviceitem.ServiceItemResponse;
import uz.shox.med.entity.Branch;
import uz.shox.med.entity.ServiceItem;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.mapper.ServiceItemMapper;
import uz.shox.med.repository.BranchRepository;
import uz.shox.med.repository.ServiceItemRepository;
import uz.shox.med.service.ServiceItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceItemServiceImpl implements ServiceItemService {

    private final ServiceItemRepository serviceItemRepository;
    private final BranchRepository branchRepository;
    private final ServiceItemMapper serviceItemMapper;

    @Override
    public List<ServiceItemResponse> getActiveServices() {
        return serviceItemRepository.findByActiveTrue()
                .stream()
                .map(serviceItemMapper::toResponse)
                .toList();
    }

    @Override
    public List<ServiceItemResponse> getServicesByBranch(Long branchId) {

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Filial topilmadi"));

        return serviceItemRepository.findByBranchAndActiveTrue(branch)
                .stream()
                .map(serviceItemMapper::toResponse)
                .toList();
    }

    @Override
    public List<ServiceItemResponse> searchByCategory(String category) {
        return serviceItemRepository.findByCategoryContainingIgnoreCaseAndActiveTrue(category)
                .stream()
                .map(serviceItemMapper::toResponse)
                .toList();
    }

    @Override
    public ServiceItemResponse getById(Long id) {

        ServiceItem serviceItem = serviceItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Xizmat topilmadi"));

        return serviceItemMapper.toResponse(serviceItem);
    }
}