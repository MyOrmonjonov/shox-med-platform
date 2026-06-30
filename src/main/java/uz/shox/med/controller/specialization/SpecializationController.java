package uz.shox.med.controller.specialization;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.specialization.SpecializationResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.service.SpecializationService;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SpecializationResponse>>> getSpecializations() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Mutaxassisliklar ro'yxati",
                        specializationService.getActiveSpecializations()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecializationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Mutaxassislik ma'lumotlari",
                        specializationService.getById(id)
                )
        );
    }
}