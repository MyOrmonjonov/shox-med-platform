package uz.shox.med.dto.dashboard;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardChartPointDto {

    private String label;

    private Long value;
}