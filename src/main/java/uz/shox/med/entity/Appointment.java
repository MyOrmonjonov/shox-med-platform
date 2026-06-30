package uz.shox.med.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.shox.med.enums.AppointmentSource;
import uz.shox.med.enums.AppointmentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Qabul raqami
     * AP-202600001
     */
    @Column(unique = true)
    private String appointmentNumber;

    /**
     * Navbat raqami
     * A001
     */
    private String queueNumber;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Enumerated(EnumType.STRING)
    private AppointmentSource source;

    private LocalDateTime appointmentTime;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    @Column(columnDefinition = "TEXT")
    private String patientComment;

    @Column(columnDefinition = "TEXT")
    private String adminComment;

    @Column(columnDefinition = "TEXT")
    private String cancelReason;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    private ServiceItem serviceItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Branch branch;

    /**
     * Kim yaratgan
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    /**
     * Kim bekor qilgan
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User cancelledBy;

    /**
     * Slot
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private DoctorTimeSlot doctorTimeSlot;


}