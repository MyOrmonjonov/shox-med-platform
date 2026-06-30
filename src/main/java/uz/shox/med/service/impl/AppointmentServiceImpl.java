package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.shox.med.dto.appointment.AppointmentCreateRequest;
import uz.shox.med.dto.appointment.AppointmentResponse;
import uz.shox.med.entity.*;
import uz.shox.med.enums.*;
import uz.shox.med.exception.BadRequestException;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.mapper.AppointmentMapper;
import uz.shox.med.notification.AppointmentNotificationBuilder;
import uz.shox.med.repository.*;
import uz.shox.med.service.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorRepository doctorRepository;
    private final BranchRepository branchRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentHistoryService appointmentHistoryService;
    private final NotificationService notificationService;
    private final AppointmentNotificationBuilder notificationBuilder;
    private final DoctorTimeSlotRepository doctorTimeSlotRepository;

    @Override
    @Transactional
    public AppointmentResponse createAppointment(User user, AppointmentCreateRequest request) {

        Patient patient = patientService.getByUser(user);

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Filial topilmadi"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Shifokor topilmadi"));

        ServiceItem serviceItem = serviceItemRepository.findById(request.getServiceItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Xizmat topilmadi"));

        DoctorTimeSlot timeSlot = doctorTimeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Vaqt sloti topilmadi"));

        if (!timeSlot.getDoctor().getId().equals(doctor.getId())) {
            throw new BadRequestException("Tanlangan slot bu shifokorga tegishli emas");
        }

        if (!timeSlot.getBranch().getId().equals(branch.getId())) {
            throw new BadRequestException("Tanlangan slot bu filialga tegishli emas");
        }

        if (timeSlot.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("O'tib ketgan vaqtga qabul yaratib bo'lmaydi");
        }

        if (timeSlot.getStatus() != SlotStatus.AVAILABLE) {
            throw new BadRequestException("Bu vaqt allaqachon band qilingan");
        }

        timeSlot.setStatus(SlotStatus.BOOKED);
        timeSlot = doctorTimeSlotRepository.save(timeSlot);

        Appointment appointment = Appointment.builder()
                .appointmentTime(timeSlot.getStartTime())
                .patientComment(request.getPatientComment())
                .patient(patient)
                .doctor(doctor)
                .branch(branch)
                .serviceItem(serviceItem)
                .doctorTimeSlot(timeSlot)
                .createdBy(user)
                .build();

        appointment.setStatus(AppointmentStatus.CREATED);
        appointment.setSource(AppointmentSource.MINI_APP);

        appointment = appointmentRepository.save(appointment);

        appointment.setAppointmentNumber("AP-" + String.format("%06d", appointment.getId()));
        appointment.setQueueNumber("A" + String.format("%03d", appointment.getId()));

        appointment = appointmentRepository.save(appointment);

        appointmentHistoryService.saveHistory(
                appointment,
                AppointmentAction.CREATE,
                null,
                AppointmentStatus.CREATED,
                user,
                null,
                "Qabul yaratildi"
        );

        notificationService.createTelegramNotification(
                user,
                NotificationType.APPOINTMENT_CREATED,
                "Qabul yaratildi",
                notificationBuilder.appointmentCreated(appointment)
        );

        return appointmentMapper.toResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> myAppointments(User user) {

        Patient patient = patientService.getByUser(user);

        return appointmentRepository.findByPatientOrderByAppointmentTimeDesc(patient)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }
}