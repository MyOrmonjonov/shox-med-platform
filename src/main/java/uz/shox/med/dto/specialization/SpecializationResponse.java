package uz.shox.med.dto.specialization;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecializationResponse {

    private Long id;

    private String nameUz;

    private String nameRu;

    private String nameEn;

    private String iconUrl;

    private Integer orderIndex;

    private Boolean active;
}