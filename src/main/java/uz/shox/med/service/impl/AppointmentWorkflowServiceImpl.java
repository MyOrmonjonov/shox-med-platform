package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.shox.med.dto.appointment.AppointmentResponse;
import uz.shox.med.entity.Appointment;
import uz.shox.med.entity.User;
import uz.shox.med.enums.AppointmentAction;
import uz.shox.med.enums.AppointmentStatus;
import uz.shox.med.exception.BadRequestException;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.mapper.AppointmentMapper;
import uz.shox.med.repository.AppointmentRepository;
import uz.shox.med.service.AppointmentHistoryService;
import uz.shox.med.service.AppointmentWorkflowService;
import uz.shox.med.enums.NotificationType;
import uz.shox.med.notification.AppointmentNotificationBuilder;
import uz.shox.med.service.NotificationService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentWorkflowServiceImpl implements AppointmentWorkflowService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentHistoryService historyService;
    private final AppointmentMapper appointmentMapper;
    private final NotificationService notificationService;
    private final AppointmentNotificationBuilder notificationBuilder;
    @Override
    @Transactional
    public AppointmentResponse confirm(Long appointmentId, User user) {
        Appointment appointment = getAppointment(appointmentId);
        AppointmentStatus oldStatus = appointment.getStatus();

        if (oldStatus != AppointmentStatus.CREATED) {
            throw new BadRequestException("Faqat CREATED statusdagi qabul tasdiqlanadi");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointment = appointmentRepository.save(appointment);

        historyService.saveHistory(
                appointment,
                AppointmentAction.CONFIRM,
                oldStatus,
                AppointmentStatus.CONFIRMED,
                user,
                null,
                "Qabul tasdiqlandi"
        );

        notificationService.createTelegramNotification(
                appointment.getPatient().getUser(),
                NotificationType.APPOINTMENT_CONFIRMED,
                "Qabul tasdiqlandi",
                notificationBuilder.appointmentConfirmed(appointment)
        );
        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse cancel(Long appointmentId, User user, String reason) {
        Appointment appointment = getAppointment(appointmentId);
        AppointmentStatus oldStatus = appointment.getStatus();

        if (oldStatus == AppointmentStatus.COMPLETED) {
            throw new BadRequestException("Tugallangan qabulni bekor qilib bo'lmaydi");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setCancelReason(reason);
        appointment.setCancelledBy(user);
        appointment = appointmentRepository.save(appointment);

        historyService.saveHistory(
                appointment,
                AppointmentAction.CANCEL,
                oldStatus,
                AppointmentStatus.CANCELLED,
                user,
                reason,
                "Qabul bekor qilindi"
        );
        notificationService.createTelegramNotification(
                appointment.getPatient().getUser(),
                NotificationType.APPOINTMENT_CANCELLED,
                "Qabul bekor qilindi",
                notificationBuilder.appointmentCancelled(appointment)
        );
        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse checkIn(Long appointmentId, User user) {
        Appointment appointment = getAppointment(appointmentId);
        AppointmentStatus oldStatus = appointment.getStatus();

        if (oldStatus != AppointmentStatus.CONFIRMED) {
            throw new BadRequestException("Faqat CONFIRMED statusdagi qabul check-in qilinadi");
        }

        appointment.setStatus(AppointmentStatus.CHECK_IN);
        appointment.setCheckInTime(LocalDateTime.now());
        appointment = appointmentRepository.save(appointment);

        historyService.saveHistory(
                appointment,
                AppointmentAction.CHECK_IN,
                oldStatus,
                AppointmentStatus.CHECK_IN,
                user,
                null,
                "Bemor klinikaga keldi"
        );

        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse start(Long appointmentId, User user) {
        Appointment appointment = getAppointment(appointmentId);
        AppointmentStatus oldStatus = appointment.getStatus();

        if (oldStatus != AppointmentStatus.CHECK_IN) {
            throw new BadRequestException("Faqat CHECK_IN statusdagi qabul boshlanadi");
        }

        appointment.setStatus(AppointmentStatus.IN_PROGRESS);
        appointment = appointmentRepository.save(appointment);

        historyService.saveHistory(
                appointment,
                AppointmentAction.START,
                oldStatus,
                AppointmentStatus.IN_PROGRESS,
                user,
                null,
                "Qabul boshlandi"
        );

        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse complete(Long appointmentId, User user) {
        Appointment appointment = getAppointment(appointmentId);
        AppointmentStatus oldStatus = appointment.getStatus();

        if (oldStatus != AppointmentStatus.IN_PROGRESS) {
            throw new BadRequestException("Faqat IN_PROGRESS statusdagi qabul tugatiladi");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setCheckOutTime(LocalDateTime.now());
        appointment = appointmentRepository.save(appointment);

        historyService.saveHistory(
                appointment,
                AppointmentAction.COMPLETE,
                oldStatus,
                AppointmentStatus.COMPLETED,
                user,
                null,
                "Qabul yakunlandi"
        );

        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse noShow(Long appointmentId, User user) {
        Appointment appointment = getAppointment(appointmentId);
        AppointmentStatus oldStatus = appointment.getStatus();

        if (oldStatus == AppointmentStatus.COMPLETED || oldStatus == AppointmentStatus.CANCELLED) {
            throw new BadRequestException("Bu qabul uchun NO_SHOW belgilab bo'lmaydi");
        }

        appointment.setStatus(AppointmentStatus.NO_SHOW);
        appointment = appointmentRepository.save(appointment);

        historyService.saveHistory(
                appointment,
                AppointmentAction.NO_SHOW,
                oldStatus,
                AppointmentStatus.NO_SHOW,
                user,
                null,
                "Bemor kelmadi"
        );

        return appointmentMapper.toResponse(appointment);
    }

    private Appointment getAppointment(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Qabul topilmadi"));
    }
}