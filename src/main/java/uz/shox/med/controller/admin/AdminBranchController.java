package uz.shox.med.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.branch.BranchFilterRequest;
import uz.shox.med.dto.branch.BranchResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.BranchAdminService;
import uz.shox.med.service.MessageService;

@RestController
@RequestMapping("/api/admin/branches")
@RequiredArgsConstructor
public class AdminBranchController {

    private final BranchAdminService branchAdminService;
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BranchResponse>>> getBranches(
            @ModelAttribute BranchFilterRequest filter,
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
                        messageService.get("admin.branches.loaded"),
                        branchAdminService.getBranches(filter, pageable)
                )
        );
    }
}