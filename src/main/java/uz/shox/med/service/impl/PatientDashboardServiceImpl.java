package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.dashboard.*;
import uz.shox.med.entity.Patient;
import uz.shox.med.entity.User;
import uz.shox.med.repository.AppointmentRepository;
import uz.shox.med.repository.LabResultRepository;
import uz.shox.med.service.PatientDashboardService;
import uz.shox.med.service.PatientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientDashboardServiceImpl implements PatientDashboardService {

    private final PatientService patientService;
    private final AppointmentRepository appointmentRepository;
    private final LabResultRepository labResultRepository;

    @Override
    public PatientDashboardResponse getDashboard(User user) {

        Patient patient = patientService.getByUser(user);

        DashboardProfileDto profile = DashboardProfileDto.builder()
                .userId(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .photoUrl(user.getPhotoUrl())
                .medicalCardNumber(patient.getMedicalCardNumber())
                .profileVerified(user.getEmailVerified())
                .build();

        DashboardAppointmentDto nextAppointment = null;

        DashboardLabResultDto latestLabResult = null;

        List<DashboardQuickActionDto> quickActions = List.of(
                DashboardQuickActionDto.builder()
                        .title("Qabulga yozilish")
                        .icon("calendar")
                        .action("BOOK_APPOINTMENT")
                        .orderIndex(1)
                        .build(),
                DashboardQuickActionDto.builder()
                        .title("Analiz natijalari")
                        .icon("file-text")
                        .action("LAB_RESULTS")
                        .orderIndex(2)
                        .build(),
                DashboardQuickActionDto.builder()
                        .title("AI yordamchi")
                        .icon("bot")
                        .action("AI_ASSISTANT")
                        .orderIndex(3)
                        .build(),
                DashboardQuickActionDto.builder()
                        .title("Profil")
                        .icon("user")
                        .action("PROFILE")
                        .orderIndex(4)
                        .build()
        );

        List<DashboardBannerDto> banners = List.of(
                DashboardBannerDto.builder()
                        .title("Shox Med")
                        .subtitle("Sog‘lig‘ingiz biz uchun muhim")
                        .imageUrl("/images/banner-main.png")
                        .actionUrl("/book")
                        .orderIndex(1)
                        .build()
        );

        return PatientDashboardResponse.builder()
                .profile(profile)
                .nextAppointment(nextAppointment)
                .latestLabResult(latestLabResult)
                .quickActions(quickActions)
                .banners(banners)
                .build();
    }
}