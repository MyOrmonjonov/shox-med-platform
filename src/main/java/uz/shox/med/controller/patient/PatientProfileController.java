package uz.shox.med.controller.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.auth.UserResponse;
import uz.shox.med.dto.dashboard.PatientDashboardResponse;
import uz.shox.med.entity.User;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.service.PatientDashboardService;
import uz.shox.med.service.PatientService;
import uz.shox.med.util.SecurityUtils;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientProfileController {

    private final PatientService patientService;
    private final PatientDashboardService dashboardService;
    private final SecurityUtils securityUtils;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyProfile() {

        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profil ma'lumotlari",
                        patientService.getMyProfile(currentUser)
                )
        );
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<PatientDashboardResponse>> dashboard() {

        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Dashboard ma'lumotlari",
                        dashboardService.getDashboard(currentUser)
                )
        );
    }
}