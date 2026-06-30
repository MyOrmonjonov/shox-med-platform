package uz.shox.med.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.doctor.DoctorFilterRequest;
import uz.shox.med.dto.doctor.DoctorResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.DoctorAdminService;
import uz.shox.med.service.MessageService;

@RestController
@RequestMapping("/api/admin/doctors")
@RequiredArgsConstructor
public class AdminDoctorController {

    private final DoctorAdminService doctorAdminService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<DoctorResponse>>> getDoctors(
            @ModelAttribute DoctorFilterRequest filter,
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
                        messageService.get("admin.doctors.loaded"),
                        doctorAdminService.getDoctors(filter, pageable)
                )
        );
    }
}