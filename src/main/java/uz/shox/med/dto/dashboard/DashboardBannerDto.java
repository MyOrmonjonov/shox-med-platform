package uz.shox.med.dto.dashboard;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardBannerDto {

    private String title;

    private String subtitle;

    private String imageUrl;

    private String actionUrl;

    private Integer orderIndex;
}