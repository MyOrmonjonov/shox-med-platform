package uz.shox.med.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String phone;

    private String email;

    private String photoUrl;

    private String role;

    private Long branchId;

    private String branchName;
}