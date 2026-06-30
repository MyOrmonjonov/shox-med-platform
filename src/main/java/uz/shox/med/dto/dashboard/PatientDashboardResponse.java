package uz.shox.med.dto.dashboard;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDashboardResponse {

    private DashboardProfileDto profile;

    private DashboardAppointmentDto nextAppointment;

    private DashboardLabResultDto latestLabResult;

    private List<DashboardQuickActionDto> quickActions;

    private List<DashboardBannerDto> banners;
}