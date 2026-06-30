package uz.shox.med.mail;

public interface MailService {

    void sendEmailVerification(String toEmail, String token);

    void sendPasswordReset(String toEmail, String token);

}