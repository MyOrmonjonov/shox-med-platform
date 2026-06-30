package uz.shox.med.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.patient.PatientFilterRequest;
import uz.shox.med.dto.patient.PatientResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.MessageService;
import uz.shox.med.service.PatientAdminService;

@RestController
@RequestMapping("/api/admin/patients")
@RequiredArgsConstructor
public class AdminPatientController {

    private final PatientAdminService patientAdminService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PatientResponse>>> getPatients(
            @ModelAttribute PatientFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                ApiResponse.success(
                        messageService.get("admin.patients.loaded"),
                        patientAdminService.getPatients(filter, pageable)
                )
        );
    }
}