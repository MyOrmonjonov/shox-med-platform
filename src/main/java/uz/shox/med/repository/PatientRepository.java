package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.Patient;
import uz.shox.med.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long>,
        JpaSpecificationExecutor<Patient> {
    Optional<Patient> findByUser(User user);
    Optional<Patient> findByMedicalCardNumber(String medicalCardNumber);
}