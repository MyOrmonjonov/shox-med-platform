package uz.shox.med.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.schedule.AvailableTimeResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.service.ScheduleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/doctors/{doctorId}/available-times")
    public ResponseEntity<ApiResponse<List<AvailableTimeResponse>>> getAvailableTimes(
            @PathVariable Long doctorId,
            @RequestParam LocalDate date
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Bo'sh vaqtlar",
                        scheduleService.getAvailableTimes(doctorId, date)
                )
        );
    }
}