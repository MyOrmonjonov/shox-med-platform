package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.entity.Branch;
import uz.shox.med.entity.Doctor;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.mapper.DoctorMapper;
import uz.shox.med.repository.BranchRepository;
import uz.shox.med.repository.DoctorRepository;
import uz.shox.med.service.DoctorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final BranchRepository branchRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public List<DoctorResponse> getActiveDoctors() {
        return doctorRepository.findByActiveTrue()
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    public List<DoctorResponse> getDoctorsByBranch(Long branchId) {

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Filial topilmadi"));

        return doctorRepository.findByBranchAndActiveTrue(branch)
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    public List<DoctorResponse> searchBySpecialization(String specialization) {
        return doctorRepository
                .findBySpecializationContainingIgnoreCaseAndActiveTrue(specialization)
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    public DoctorResponse getById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shifokor topilmadi"));

        return doctorMapper.toResponse(doctor);
    }
}