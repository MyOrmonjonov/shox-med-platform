package uz.shox.med.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "Email, telefon yoki tibbiy karta raqami majburiy")
    private String login;

    @NotBlank(message = "Parol majburiy")
    private String password;

    @Builder.Default
    private boolean rememberMe = false;

}