package uz.shox.med.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double price;

    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    private Branch branch;
}