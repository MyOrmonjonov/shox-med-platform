package uz.shox.med.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shox.med.dto.auth.*;
import uz.shox.med.response.ApiResponse;
import uz.shox.med.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<LoginResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ro'yxatdan o'tish muvaffaqiyatli yakunlandi",
                        authService.register(request)
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Tizimga muvaffaqiyatli kirildi",
                        authService.login(request)
                )
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(
            @RequestParam String refreshToken
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Token yangilandi",
                        authService.refreshToken(refreshToken)
                )
        );
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(
            @RequestParam String token
    ) {

        authService.verifyEmail(token);

        return ResponseEntity.ok(
                ApiResponse.success("Email muvaffaqiyatli tasdiqlandi")
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {

        authService.forgotPassword(request);

        return ResponseEntity.ok(
                ApiResponse.success("Parolni tiklash havolasi emailingizga yuborildi")
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {

        authService.resetPassword(request);

        return ResponseEntity.ok(
                ApiResponse.success("Parol muvaffaqiyatli o'zgartirildi")
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @RequestParam String refreshToken
    ) {

        authService.logout(refreshToken);

        return ResponseEntity.ok(
                ApiResponse.success("Tizimdan muvaffaqiyatli chiqildi")
        );
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResponse<String>> resendVerification(
            @RequestParam String email
    ) {

        authService.resendVerificationEmail(email);

        return ResponseEntity.ok(
                ApiResponse.success("Tasdiqlash emaili qayta yuborildi")
        );
    }
}