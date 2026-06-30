package uz.shox.med.dto.home;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeQuickActionDto {

    private String title;

    private String subtitle;

    private String icon;

    private String action;

    private Integer orderIndex;
}