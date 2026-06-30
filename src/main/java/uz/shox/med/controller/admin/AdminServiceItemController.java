package uz.shox.med.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.serviceitem.ServiceItemFilterRequest;
import uz.shox.med.dto.serviceitem.ServiceItemResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.MessageService;
import uz.shox.med.service.ServiceItemAdminService;

@RestController
@RequestMapping("/api/admin/services")
@RequiredArgsConstructor
public class AdminServiceItemController {

    private final ServiceItemAdminService serviceItemAdminService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ServiceItemResponse>>> getServices(
            @ModelAttribute ServiceItemFilterRequest filter,
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
                        messageService.get("admin.services.loaded"),
                        serviceItemAdminService.getServices(filter, pageable)
                )
        );
    }
}