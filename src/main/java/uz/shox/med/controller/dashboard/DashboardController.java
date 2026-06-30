package uz.shox.med.controller.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.shox.med.dto.dashboard.DashboardChartPointDto;
import uz.shox.med.dto.dashboard.DashboardRecentAppointmentDto;
import uz.shox.med.dto.dashboard.DashboardResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.DashboardService;
import uz.shox.med.service.MessageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        messageService.get("dashboard.loaded"),
                        dashboardService.getDashboard()
                )
        );
    }

    @GetMapping("/recent-appointments")
    public ResponseEntity<ApiResponse<PageResponse<DashboardRecentAppointmentDto>>> recentAppointments(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                ApiResponse.success(
                        messageService.get("dashboard.recent.loaded"),
                        dashboardService.getRecentAppointments(pageable)
                )
        );
    }

    @GetMapping("/weekly-appointments")
    public ResponseEntity<ApiResponse<List<DashboardChartPointDto>>> weeklyAppointments() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        messageService.get("dashboard.weekly.loaded"),
                        dashboardService.getWeeklyAppointments()
                )
        );
    }
}