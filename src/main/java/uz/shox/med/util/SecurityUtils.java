package uz.shox.med.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.shox.med.entity.User;
import uz.shox.med.exception.UnauthorizedException;
import uz.shox.med.security.UserPrincipal;

@Component
public class SecurityUtils {

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Foydalanuvchi tizimga kirmagan");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserPrincipal userPrincipal)) {
            throw new UnauthorizedException("Foydalanuvchi aniqlanmadi");
        }

        return userPrincipal.getUser();
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    public String getCurrentUserPhone() {
        return getCurrentUser().getPhone();
    }

    public boolean hasRole(String role) {
        return getCurrentUser()
                .getRole()
                .getName()
                .name()
                .equalsIgnoreCase(role);
    }
}