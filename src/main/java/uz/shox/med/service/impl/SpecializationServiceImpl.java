package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.specialization.SpecializationResponse;
import uz.shox.med.entity.Specialization;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.mapper.SpecializationMapper;
import uz.shox.med.repository.SpecializationRepository;
import uz.shox.med.service.SpecializationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final SpecializationMapper specializationMapper;

    @Override
    public List<SpecializationResponse> getActiveSpecializations() {
        return specializationRepository.findByActiveTrueOrderByOrderIndexAsc()
                .stream()
                .map(specializationMapper::toResponse)
                .toList();
    }

    @Override
    public SpecializationResponse getById(Long id) {
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mutaxassislik topilmadi"));

        return specializationMapper.toResponse(specialization);
    }
}