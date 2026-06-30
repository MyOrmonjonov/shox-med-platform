package uz.shox.med.controller.serviceitem;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.serviceitem.ServiceItemResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.service.ServiceItemService;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceItemController {

    private final ServiceItemService serviceItemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceItemResponse>>> getServices() {
        return ResponseEntity.ok(
                ApiResponse.success("Xizmatlar ro'yxati", serviceItemService.getActiveServices())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceItemResponse>> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Xizmat ma'lumotlari", serviceItemService.getById(id))
        );
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<ApiResponse<List<ServiceItemResponse>>> getServicesByBranch(
            @PathVariable Long branchId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Filial bo'yicha xizmatlar", serviceItemService.getServicesByBranch(branchId))
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ServiceItemResponse>>> searchByCategory(
            @RequestParam String category
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Qidiruv natijalari", serviceItemService.searchByCategory(category))
        );
    }
}