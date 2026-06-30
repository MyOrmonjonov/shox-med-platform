package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.appointment.AppointmentResponse;
import uz.shox.med.dto.branch.BranchResponse;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.dto.home.*;
import uz.shox.med.dto.specialization.SpecializationResponse;
import uz.shox.med.entity.Patient;
import uz.shox.med.entity.User;
import uz.shox.med.mapper.AppointmentMapper;
import uz.shox.med.mapper.BranchMapper;
import uz.shox.med.mapper.DoctorMapper;
import uz.shox.med.mapper.SpecializationMapper;
import uz.shox.med.repository.AppointmentRepository;
import uz.shox.med.repository.BranchRepository;
import uz.shox.med.repository.DoctorRepository;
import uz.shox.med.repository.NotificationRepository;
import uz.shox.med.repository.SpecializationRepository;
import uz.shox.med.service.MiniAppHomeService;
import uz.shox.med.service.PatientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MiniAppHomeServiceImpl implements MiniAppHomeService {

    private final PatientService patientService;
    private final AppointmentRepository appointmentRepository;
    private final BranchRepository branchRepository;
    private final SpecializationRepository specializationRepository;
    private final DoctorRepository doctorRepository;
    private final NotificationRepository notificationRepository;

    private final AppointmentMapper appointmentMapper;
    private final BranchMapper branchMapper;
    private final SpecializationMapper specializationMapper;
    private final DoctorMapper doctorMapper;

    @Override
    public MiniAppHomeResponse getHome(User user) {

        Patient patient = patientService.getByUser(user);

        AppointmentResponse nextAppointment = appointmentRepository
                .findByPatientOrderByAppointmentTimeDesc(patient)
                .stream()
                .filter(a -> a.getStatus() != null)
                .findFirst()
                .map(appointmentMapper::toResponse)
                .orElse(null);

        HomeProfileDto profile = HomeProfileDto.builder()
                .userId(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .photoUrl(user.getPhotoUrl())
                .medicalCardNumber(patient.getMedicalCardNumber())
                .verified(Boolean.TRUE.equals(user.getPhoneVerified()) || Boolean.TRUE.equals(user.getEmailVerified()))
                .language("UZ")
                .build();

        List<HomeQuickActionDto> quickActions = List.of(
                HomeQuickActionDto.builder()
                        .title("Qabulga yozilish")
                        .subtitle("Shifokor va vaqt tanlang")
                        .icon("calendar-plus")
                        .action("BOOK_APPOINTMENT")
                        .orderIndex(1)
                        .build(),
                HomeQuickActionDto.builder()
                        .title("Mening qabullarim")
                        .subtitle("Yaqin va oldingi qabullar")
                        .icon("calendar-check")
                        .action("MY_APPOINTMENTS")
                        .orderIndex(2)
                        .build(),
                HomeQuickActionDto.builder()
                        .title("Analiz natijalari")
                        .subtitle("PDF va tarix")
                        .icon("file-text")
                        .action("LAB_RESULTS")
                        .orderIndex(3)
                        .build(),
                HomeQuickActionDto.builder()
                        .title("AI yordamchi")
                        .subtitle("Savol bering")
                        .icon("bot")
                        .action("AI_ASSISTANT")
                        .orderIndex(4)
                        .build()
        );

        return MiniAppHomeResponse.builder()
                .profile(profile)
                .nextAppointment(nextAppointment)
                .quickActions(quickActions)
                .build();
    }

    @Override
    public List<HomeBannerDto> getBanners() {
        return List.of(
                HomeBannerDto.builder()
                        .title("Shox Med")
                        .subtitle("Sog‘lig‘ingiz biz uchun muhim")
                        .imageUrl("/images/banners/main.png")
                        .actionUrl("/book")
                        .orderIndex(1)
                        .build()
        );
    }

    @Override
    public List<BranchResponse> getBranches() {
        return branchRepository.findByActiveTrue()
                .stream()
                .map(branchMapper::toResponse)
                .toList();
    }

    @Override
    public List<SpecializationResponse> getSpecializations() {
        return specializationRepository.findByActiveTrueOrderByOrderIndexAsc()
                .stream()
                .map(specializationMapper::toResponse)
                .toList();
    }

    @Override
    public List<DoctorResponse> getPopularDoctors() {
        return doctorRepository.findByActiveTrue()
                .stream()
                .limit(10)
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    public List<HomeNotificationDto> getNotifications(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .limit(10)
                .map(n -> HomeNotificationDto.builder()
                        .id(n.getId())
                        .title(n.getTitle())
                        .message(n.getMessage())
                        .type(n.getType() != null ? n.getType().name() : null)
                        .createdAt(n.getCreatedAt())
                        .build())
                .toList();
    }
}