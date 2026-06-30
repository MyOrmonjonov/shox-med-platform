package uz.shox.med.service;

import org.springframework.data.domain.Pageable;
import uz.shox.med.dto.branch.BranchFilterRequest;
import uz.shox.med.dto.branch.BranchResponse;
import uz.shox.med.response.PageResponse;

public interface BranchAdminService {

    PageResponse<BranchResponse> getBranches(
            BranchFilterRequest filter,
            Pageable pageable
    );
}