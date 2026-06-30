package uz.shox.med.dto.dashboard;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private Long totalPatients;
    private Long totalDoctors;
    private Long totalBranches;
    private Long totalAppointments;

    private Long todayAppointments;

    private Long confirmedAppointments;
    private Long waitingAppointments;
    private Long inProgressAppointments;
    private Long completedAppointments;
    private Long cancelledAppointments;

    private List<DashboardTodayAppointmentDto> todayAppointmentsList;
    private List<DashboardRecentAppointmentDto> recentAppointments;
}