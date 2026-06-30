package uz.shox.med.mapper;

import org.springframework.stereotype.Component;
import uz.shox.med.dto.auth.UserResponse;
import uz.shox.med.entity.User;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .role(user.getRole() != null ? user.getRole().getName().name() : null)
                .branchId(user.getBranch() != null ? user.getBranch().getId() : null)
                .branchName(user.getBranch() != null ? user.getBranch().getName() : null)
                .build();
    }
}