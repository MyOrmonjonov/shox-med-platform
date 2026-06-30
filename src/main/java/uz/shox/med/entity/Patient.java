package uz.shox.med.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicalCardNumber;

    private String address;

    private String emergencyContactName;

    private String emergencyContactPhone;

    private String bloodType;

    private String allergies;

    private String chronicDiseases;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}