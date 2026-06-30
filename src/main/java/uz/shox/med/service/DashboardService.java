package uz.shox.med.service;
import org.springframework.data.domain.Pageable;
import uz.shox.med.dto.dashboard.DashboardChartPointDto;
import uz.shox.med.dto.dashboard.DashboardRecentAppointmentDto;
import uz.shox.med.response.PageResponse;
import uz.shox.med.dto.dashboard.DashboardResponse;

import java.util.List;

public interface DashboardService {

    DashboardResponse getDashboard();
    PageResponse<DashboardRecentAppointmentDto> getRecentAppointments(Pageable pageable);
    List<DashboardChartPointDto> getWeeklyAppointments();
}