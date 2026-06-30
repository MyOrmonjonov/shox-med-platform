package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.dashboard.DashboardResponse;
import uz.shox.med.dto.dashboard.DashboardTodayAppointmentDto;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.entity.Branch;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.Specialization;
import uz.shox.med.enums.AppointmentStatus;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.mapper.DoctorMapper;
import uz.shox.med.repository.*;
import uz.shox.med.service.DoctorService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final BranchRepository branchRepository;
    private final DoctorMapper doctorMapper;
    private final SpecializationRepository specializationRepository;


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

    @Override
    public List<DoctorResponse> getDoctorsBySpecialization(Long specializationId) {

        Specialization specialization = specializationRepository.findById(specializationId)
                .orElseThrow(() -> new ResourceNotFoundException("Mutaxassislik topilmadi"));

        return doctorRepository.findBySpecializationEntityAndActiveTrue(specialization)
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }


    @Override
    public List<DoctorResponse> getDoctorsByBranchAndSpecialization(Long branchId, Long specializationId) {

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Filial topilmadi"));

        Specialization specialization = specializationRepository.findById(specializationId)
                .orElseThrow(() -> new ResourceNotFoundException("Mutaxassislik topilmadi"));

        return doctorRepository.findByBranchAndSpecializationEntityAndActiveTrue(branch, specialization)
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }



}