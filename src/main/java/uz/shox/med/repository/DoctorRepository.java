package uz.shox.med.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.Branch;
import uz.shox.med.entity.User;
import uz.shox.med.entity.Specialization;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long>,
        JpaSpecificationExecutor<Doctor> {
    Optional<Doctor> findByUser(User user);
    List<Doctor> findByBranchAndActiveTrue(Branch branch);
    List<Doctor> findByActiveTrue();
    List<Doctor> findBySpecializationContainingIgnoreCaseAndActiveTrue(String specialization);
    List<Doctor> findBySpecializationEntityAndActiveTrue(Specialization specialization);
    List<Doctor> findByBranchAndSpecializationEntityAndActiveTrue(
            Branch branch,
            Specialization specialization
    );
}