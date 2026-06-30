package uz.shox.med.dto.home;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeBannerDto {

    private String title;

    private String subtitle;

    private String imageUrl;

    private String actionUrl;

    private Integer orderIndex;
}