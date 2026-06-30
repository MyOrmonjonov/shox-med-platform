package uz.shox.med.service;

import uz.shox.med.dto.branch.BranchResponse;

import java.util.List;

public interface BranchService {

    List<BranchResponse> getActiveBranches();

    BranchResponse getById(Long id);
}