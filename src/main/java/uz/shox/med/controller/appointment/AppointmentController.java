package uz.shox.med.controller.appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.appointment.AppointmentCreateRequest;
import uz.shox.med.dto.appointment.AppointmentHistoryResponse;
import uz.shox.med.dto.appointment.AppointmentResponse;
import uz.shox.med.entity.User;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.service.AppointmentHistoryService;
import uz.shox.med.service.AppointmentService;
import uz.shox.med.util.SecurityUtils;
import uz.shox.med.dto.appointment.AppointmentCancelRequest;
import uz.shox.med.service.AppointmentWorkflowService;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final SecurityUtils securityUtils;
    private final AppointmentWorkflowService workflowService;
    private final AppointmentHistoryService appointmentHistoryService;
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(
            @Valid @RequestBody AppointmentCreateRequest request
    ) {
        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Qabul muvaffaqiyatli yaratildi",
                        appointmentService.createAppointment(currentUser, request)
                )
        );
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> myAppointments() {

        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Mening qabullarim",
                        appointmentService.myAppointments(currentUser)
                )
        );
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<AppointmentResponse>> confirm(
            @PathVariable Long id
    ) {
        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Qabul tasdiqlandi",
                        workflowService.confirm(id, currentUser)
                )
        );
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<AppointmentResponse>> cancel(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentCancelRequest request
    ) {
        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Qabul bekor qilindi",
                        workflowService.cancel(id, currentUser, request.getReason())
                )
        );
    }
    @PostMapping("/{id}/check-in")
    public ResponseEntity<ApiResponse<AppointmentResponse>> checkIn(
            @PathVariable Long id
    ) {
        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Bemor klinikaga keldi",
                        workflowService.checkIn(id, currentUser)
                )
        );
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<ApiResponse<AppointmentResponse>> start(
            @PathVariable Long id
    ) {
        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Qabul boshlandi",
                        workflowService.start(id, currentUser)
                )
        );
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<AppointmentResponse>> complete(
            @PathVariable Long id
    ) {
        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Qabul yakunlandi",
                        workflowService.complete(id, currentUser)
                )
        );
    }

    @PostMapping("/{id}/no-show")
    public ResponseEntity<ApiResponse<AppointmentResponse>> noShow(
            @PathVariable Long id
    ) {
        User currentUser = securityUtils.getCurrentUser();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Bemor kelmadi deb belgilandi",
                        workflowService.noShow(id, currentUser)
                )
        );
    }
    @GetMapping("/{id}/history")
    public ResponseEntity<ApiResponse<List<AppointmentHistoryResponse>>> history(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Qabul tarixi",
                        appointmentHistoryService.getHistory(id)
                )
        );
    }
}