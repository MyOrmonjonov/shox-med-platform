package uz.shox.med.dto.dashboard;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardQuickActionDto {

    private String title;

    private String icon;

    private String action;

    private Integer orderIndex;
}