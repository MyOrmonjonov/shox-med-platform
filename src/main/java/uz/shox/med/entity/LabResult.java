package uz.shox.med.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.shox.med.enums.LabResultStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "lab_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;

    private String testType;

    @Enumerated(EnumType.STRING)
    private LabResultStatus status = LabResultStatus.IN_PROGRESS;

    private String pdfUrl;

    @Column(columnDefinition = "TEXT")
    private String resultText;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    private User uploadedBy;

    private LocalDateTime resultDate;
}