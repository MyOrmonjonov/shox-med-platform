package uz.shox.med.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {

    @NotBlank(message = "Token majburiy")
    private String token;

    @NotBlank(message = "Yangi parol majburiy")
    @Size(min = 8, message = "Parol kamida 8 ta belgidan iborat bo'lishi kerak")
    private String newPassword;

    @NotBlank(message = "Parolni tasdiqlash majburiy")
    private String confirmPassword;
}