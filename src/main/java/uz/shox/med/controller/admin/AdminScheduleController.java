package uz.shox.med.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.service.DoctorTimeSlotGeneratorService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/schedule")
@RequiredArgsConstructor
public class AdminScheduleController {

    private final DoctorTimeSlotGeneratorService generatorService;

    @PostMapping("/doctors/{doctorId}/generate-slots")
    public ResponseEntity<ApiResponse<String>> generateForDoctor(
            @PathVariable Long doctorId,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
    ) {
        generatorService.generateSlotsForDoctor(doctorId, fromDate, toDate);

        return ResponseEntity.ok(
                ApiResponse.success("Shifokor uchun slotlar yaratildi")
        );
    }

    @PostMapping("/generate-slots")
    public ResponseEntity<ApiResponse<String>> generateForAllDoctors(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
    ) {
        generatorService.generateSlotsForAllDoctors(fromDate, toDate);

        return ResponseEntity.ok(
                ApiResponse.success("Barcha shifokorlar uchun slotlar yaratildi")
        );
    }
}