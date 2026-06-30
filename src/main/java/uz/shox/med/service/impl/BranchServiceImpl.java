package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shox.med.dto.branch.BranchResponse;
import uz.shox.med.entity.Branch;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.mapper.BranchMapper;
import uz.shox.med.repository.BranchRepository;
import uz.shox.med.service.BranchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public List<BranchResponse> getActiveBranches() {
        return branchRepository.findByActiveTrue()
                .stream()
                .map(branchMapper::toResponse)
                .toList();
    }

    @Override
    public BranchResponse getById(Long id) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filial topilmadi"));

        return branchMapper.toResponse(branch);
    }
}