package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.branch.BranchFilterRequest;
import uz.shox.med.dto.branch.BranchResponse;
import uz.shox.med.entity.Branch;
import uz.shox.med.mapper.BranchMapper;
import uz.shox.med.repository.BranchRepository;
import uz.shox.med.response.PageResponse;
import uz.shox.med.service.BranchAdminService;
import uz.shox.med.specification.BranchSpecification;

@Service
@RequiredArgsConstructor
public class BranchAdminServiceImpl implements BranchAdminService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public PageResponse<BranchResponse> getBranches(
            BranchFilterRequest filter,
            Pageable pageable
    ) {
        Page<BranchResponse> page = branchRepository
                .findAll(BranchSpecification.filter(filter), pageable)
                .map(branchMapper::toResponse);

        return PageResponse.of(page);
    }
}