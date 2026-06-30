package uz.shox.med.dto.serviceitem;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceItemResponse {

    private Long id;

    private String name;

    private String category;

    private String description;

    private Double price;

    private Long branchId;

    private String branchName;

    private Boolean active;
}