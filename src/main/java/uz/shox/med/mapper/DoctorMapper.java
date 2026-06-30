package uz.shox.med.mapper;

import org.springframework.stereotype.Component;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.entity.Doctor;
import uz.shox.med.entity.User;

@Component
public class DoctorMapper {

    public DoctorResponse toResponse(Doctor doctor) {

        if (doctor == null) {
            return null;
        }

        User user = doctor.getUser();

        String fullName = user != null
                ? (user.getFirstName() + " " + user.getLastName())
                : null;

        return DoctorResponse.builder()
                .id(doctor.getId())
                .userId(user != null ? user.getId() : null)
                .fullName(fullName)
                .photoUrl(user != null ? user.getPhotoUrl() : null)
                .specialization(doctor.getSpecialization())
                .experienceYears(doctor.getExperienceYears())
                .education(doctor.getEducation())
                .certificates(doctor.getCertificates())
                .bio(doctor.getBio())
                .consultationPrice(doctor.getConsultationPrice())
                .workingSchedule(doctor.getWorkingSchedule())
                .branchId(doctor.getBranch() != null ? doctor.getBranch().getId() : null)
                .branchName(doctor.getBranch() != null ? doctor.getBranch().getName() : null)
                .active(doctor.getActive())
                .build();
    }
}