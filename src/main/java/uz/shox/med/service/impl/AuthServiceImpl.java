package uz.shox.med.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.shox.med.dto.auth.*;
import uz.shox.med.entity.*;
import uz.shox.med.enums.RoleName;
import uz.shox.med.exception.BadRequestException;
import uz.shox.med.exception.ResourceNotFoundException;
import uz.shox.med.exception.UnauthorizedException;
import uz.shox.med.mail.MailService;
import uz.shox.med.mapper.UserMapper;
import uz.shox.med.repository.*;
import uz.shox.med.security.JwtService;
import uz.shox.med.service.AuthService;
import uz.shox.med.service.RefreshTokenService;
import uz.shox.med.service.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final MailService mailService;

    @Override
    @Transactional
    public LoginResponse register(RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Parollar mos emas");
        }

        if (userService.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Bu email allaqachon mavjud");
        }

        if (userService.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Bu telefon allaqachon mavjud");
        }

        Role patientRole = roleRepository.findByName(RoleName.PATIENT)
                .orElseThrow(() -> new ResourceNotFoundException("PATIENT roli topilmadi"));

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .role(patientRole)
                .enabled(true)
                .emailVerified(false)
                .phoneVerified(false)
                .build();

        user = userService.save(user);

        Patient patient = Patient.builder()
                .user(user)
                .medicalCardNumber("SM-" + String.format("%06d", user.getId()))
                .build();

        patientRepository.save(patient);

        String verifyToken = UUID.randomUUID().toString();

        EmailVerificationToken emailToken = EmailVerificationToken.builder()
                .token(verifyToken)
                .user(user)
                .expiresAt(LocalDateTime.now().plusHours(24))
                .used(false)
                .build();

        emailVerificationTokenRepository.save(emailToken);

        mailService.sendEmailVerification(user.getEmail(), verifyToken);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(86400000L)
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userService.findByEmailOrPhone(request.getLogin())
                .orElseThrow(() -> new UnauthorizedException("Login yoki parol noto'g'ri"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Login yoki parol noto'g'ri");
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new UnauthorizedException("Akkaunt bloklangan");
        }

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(86400000L)
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    public LoginResponse refreshToken(String refreshTokenValue) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenValue);

        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(86400000L)
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {

        EmailVerificationToken emailToken = emailVerificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Token noto'g'ri"));

        if (Boolean.TRUE.equals(emailToken.getUsed())) {
            throw new BadRequestException("Token allaqachon ishlatilgan");
        }

        if (emailToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token muddati tugagan");
        }

        User user = emailToken.getUser();
        user.setEmailVerified(true);
        userService.save(user);

        emailToken.setUsed(true);
        emailVerificationTokenRepository.save(emailToken);
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {

        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Bu email bo‘yicha foydalanuvchi topilmadi"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .used(false)
                .build();

        passwordResetTokenRepository.save(resetToken);

        mailService.sendPasswordReset(user.getEmail(), token);
    }
    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Parollar mos emas");
        }

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new BadRequestException("Token noto‘g‘ri"));

        if (Boolean.TRUE.equals(resetToken.getUsed())) {
            throw new BadRequestException("Token allaqachon ishlatilgan");
        }

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token muddati tugagan");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        refreshTokenService.deleteByUser(user);
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenService.revokeToken(refreshToken);
    }

    @Override
    @Transactional
    public void resendVerificationEmail(String email) {

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Bu email bo‘yicha foydalanuvchi topilmadi"));

        if (Boolean.TRUE.equals(user.getEmailVerified())) {
            throw new BadRequestException("Email allaqachon tasdiqlangan");
        }

        String token = UUID.randomUUID().toString();

        EmailVerificationToken emailToken = EmailVerificationToken.builder()
                .token(token)
                .user(user)
                .expiresAt(LocalDateTime.now().plusHours(24))
                .used(false)
                .build();

        emailVerificationTokenRepository.save(emailToken);

        mailService.sendEmailVerification(user.getEmail(), token);
    }
}