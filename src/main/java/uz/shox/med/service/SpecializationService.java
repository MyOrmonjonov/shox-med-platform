package uz.shox.med.service;

import uz.shox.med.dto.specialization.SpecializationResponse;

import java.util.List;

public interface SpecializationService {

    List<SpecializationResponse> getActiveSpecializations();

    SpecializationResponse getById(Long id);
}