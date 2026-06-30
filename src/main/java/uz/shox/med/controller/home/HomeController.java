package uz.shox.med.controller.home;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.branch.BranchResponse;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.dto.home.*;
import uz.shox.med.dto.specialization.SpecializationResponse;
import uz.shox.med.entity.User;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.security.UserPrincipal;
import uz.shox.med.service.MessageService;
import uz.shox.med.service.MiniAppHomeService;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final MiniAppHomeService miniAppHomeService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<ApiResponse<MiniAppHomeResponse>> home(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        User user = principal.getUser();

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("home.loaded"), miniAppHomeService.getHome(user))
        );
    }

    @GetMapping("/branches")
    public ResponseEntity<ApiResponse<List<BranchResponse>>> branches() {
        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("home.branches.loaded"), miniAppHomeService.getBranches())
        );
    }

    @GetMapping("/specializations")
    public ResponseEntity<ApiResponse<List<SpecializationResponse>>> specializations() {
        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("home.specializations.loaded"), miniAppHomeService.getSpecializations())
        );
    }

    @GetMapping("/popular-doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> popularDoctors() {
        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("home.popular_doctors.loaded"), miniAppHomeService.getPopularDoctors())
        );
    }

    @GetMapping("/banners")
    public ResponseEntity<ApiResponse<List<HomeBannerDto>>> banners() {
        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("home.banners.loaded"), miniAppHomeService.getBanners())
        );
    }

    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse<List<HomeNotificationDto>>> notifications(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        User user = principal.getUser();

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get("home.notifications.loaded"), miniAppHomeService.getNotifications(user))
        );
    }
}