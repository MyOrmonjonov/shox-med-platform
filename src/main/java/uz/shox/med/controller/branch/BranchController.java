package uz.shox.med.controller.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.branch.BranchResponse;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.service.BranchService;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BranchResponse>>> getBranches() {
        return ResponseEntity.ok(
                ApiResponse.success("Filiallar ro'yxati", branchService.getActiveBranches())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BranchResponse>> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Filial ma'lumotlari", branchService.getById(id))
        );
    }
}