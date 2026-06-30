package uz.shox.med.dto.home;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeProfileDto {

    private Long userId;

    private String fullName;

    private String photoUrl;

    private String medicalCardNumber;

    private Boolean verified;

    private String language;
}