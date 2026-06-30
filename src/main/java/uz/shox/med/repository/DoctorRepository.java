package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.Branch;
import uz.shox.med.entity.User;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(User user);
    List<Doctor> findByBranchAndActiveTrue(Branch branch);
    List<Doctor> findByActiveTrue();
    List<Doctor> findBySpecializationContainingIgnoreCaseAndActiveTrue(String specialization);
}