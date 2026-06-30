package uz.shox.med.dto.serviceitem;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceItemFilterRequest {

    private String search;

    private String category;

    private Long branchId;

    private Boolean active;
}