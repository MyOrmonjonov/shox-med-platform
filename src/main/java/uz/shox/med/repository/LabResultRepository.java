package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.LabResult;
import uz.shox.med.entity.Patient;
import uz.shox.med.enums.LabResultStatus;

import java.util.List;

public interface LabResultRepository extends JpaRepository<LabResult, Long> {
    List<LabResult> findByPatientOrderByCreatedAtDesc(Patient patient);
    List<LabResult> findByStatus(LabResultStatus status);
}