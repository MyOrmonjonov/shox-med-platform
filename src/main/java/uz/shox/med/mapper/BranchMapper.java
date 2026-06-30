package uz.shox.med.mapper;

import org.springframework.stereotype.Component;
import uz.shox.med.dto.branch.BranchResponse;
import uz.shox.med.entity.Branch;

@Component
public class BranchMapper {

    public BranchResponse toResponse(Branch branch) {

        if (branch == null) {
            return null;
        }

        return BranchResponse.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .latitude(branch.getLatitude())
                .longitude(branch.getLongitude())
                .workingTime(branch.getWorkingTime())
                .photoUrl(branch.getPhotoUrl())
                .active(branch.getActive())
                .build();
    }
}