package uz.shox.med.dto.branch;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchFilterRequest {

    private String search;

    private Boolean active;
}