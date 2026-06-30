package uz.shox.med.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.shox.med.enums.AppointmentAction;
import uz.shox.med.enums.AppointmentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AppointmentAction action;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus newStatus;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime actionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    private User changedBy;
}