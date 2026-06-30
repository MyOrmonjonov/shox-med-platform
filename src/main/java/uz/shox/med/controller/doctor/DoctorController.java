package uz.shox.med.controller.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctors() {
        return ResponseEntity.ok(
                ApiResponse.success("Shifokorlar ro'yxati", doctorService.getActiveDoctors())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Shifokor ma'lumotlari", doctorService.getById(id))
        );
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctorsByBranch(
            @PathVariable Long branchId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Filial bo'yicha shifokorlar", doctorService.getDoctorsByBranch(branchId))
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> searchBySpecialization(
            @RequestParam String specialization
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Qidiruv natijalari", doctorService.searchBySpecialization(specialization))
        );
    }


    @GetMapping("/specialization/{specializationId}")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctorsBySpecialization(
            @PathVariable Long specializationId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Mutaxassislik bo'yicha shifokorlar",
                        doctorService.getDoctorsBySpecialization(specializationId)
                )
        );
    }

    @GetMapping("/branch/{branchId}/specialization/{specializationId}")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctorsByBranchAndSpecialization(
            @PathVariable Long branchId,
            @PathVariable Long specializationId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Filial va mutaxassislik bo'yicha shifokorlar",
                        doctorService.getDoctorsByBranchAndSpecialization(branchId, specializationId)
                )
        );
    }
}