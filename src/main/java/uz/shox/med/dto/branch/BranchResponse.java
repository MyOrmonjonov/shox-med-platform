package uz.shox.med.dto.branch;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponse {

    private Long id;

    private String name;

    private String address;

    private String phone;

    private Double latitude;

    private Double longitude;

    private String workingTime;

    private String photoUrl;

    private Boolean active;
}