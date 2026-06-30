package uz.shox.med.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url:http://localhost:8080}")
    private String frontendUrl;

    @Override
    public void sendEmailVerification(String toEmail, String token) {

        String link = frontendUrl + "/verify-email?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Shox Med - Email tasdiqlash");
        message.setText(
                "Assalomu alaykum!\n\n" +
                        "Emailingizni tasdiqlash uchun quyidagi linkka bosing:\n" +
                        link + "\n\n" +
                        "Agar bu siz bo'lmasangiz, xabarni e'tiborsiz qoldiring."
        );

        mailSender.send(message);
    }

    @Override
    public void sendPasswordReset(String toEmail, String token) {

        String link = frontendUrl + "/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Shox Med - Parolni tiklash");
        message.setText(
                "Assalomu alaykum!\n\n" +
                        "Parolni tiklash uchun quyidagi linkka bosing:\n" +
                        link + "\n\n" +
                        "Agar siz parol tiklashni so'ramagan bo'lsangiz, xabarni e'tiborsiz qoldiring."
        );

        mailSender.send(message);
    }
}