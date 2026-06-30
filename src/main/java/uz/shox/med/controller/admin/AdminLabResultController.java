package uz.shox.med.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.lab.LabResultFilterRequest;
import uz.shox.med.dto.lab.LabResultResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.LabResultAdminService;
import uz.shox.med.service.MessageService;

@RestController
@RequestMapping("/api/admin/lab-results")
@RequiredArgsConstructor
public class AdminLabResultController {

    private final LabResultAdminService labResultAdminService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<LabResultResponse>>> getLabResults(

            @ModelAttribute LabResultFilterRequest filter,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "20") int size,

            @RequestParam(defaultValue = "resultDate") String sortBy,

            @RequestParam(defaultValue = "desc") String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                ApiResponse.success(
                        messageService.get("admin.lab_results.loaded"),
                        labResultAdminService.getLabResults(filter, pageable)
                )
        );
    }
}