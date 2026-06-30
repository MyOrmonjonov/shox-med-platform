package uz.shox.med.dto.auth;

import jakarta.validation.constraints.*;
import lombok.*;
import uz.shox.med.enums.Gender;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "Ism majburiy")
    private String firstName;

    @NotBlank(message = "Familiya majburiy")
    private String lastName;

    private String middleName;

    @NotBlank(message = "Telefon majburiy")
    private String phone;

    @NotBlank(message = "Email majburiy")
    @Email(message = "Email noto'g'ri formatda")
    private String email;

    @NotBlank(message = "Parol majburiy")
    @Size(min = 8, message = "Parol kamida 8 ta belgidan iborat bo'lishi kerak")
    private String password;

    @NotBlank(message = "Parolni tasdiqlash majburiy")
    private String confirmPassword;

    private LocalDate birthDate;

    private Gender gender;
}