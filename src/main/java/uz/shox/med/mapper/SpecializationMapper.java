package uz.shox.med.mapper;

import org.springframework.stereotype.Component;
import uz.shox.med.dto.specialization.SpecializationResponse;
import uz.shox.med.entity.Specialization;

@Component
public class SpecializationMapper {

    public SpecializationResponse toResponse(Specialization specialization) {

        if (specialization == null) {
            return null;
        }

        return SpecializationResponse.builder()
                .id(specialization.getId())
                .nameUz(specialization.getNameUz())
                .nameRu(specialization.getNameRu())
                .nameEn(specialization.getNameEn())
                .iconUrl(specialization.getIconUrl())
                .orderIndex(specialization.getOrderIndex())
                .active(specialization.getActive())
                .build();
    }
}