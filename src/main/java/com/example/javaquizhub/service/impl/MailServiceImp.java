package com.example.javaquizhub.service.impl;

import com.example.javaquizhub.model.User;
import com.example.javaquizhub.service.MailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImp implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendForgotPasswordTokenLetter(String appUrl, String changePasswordToken, User user) {

        final String activationUrl = appUrl+ "?token="+ changePasswordToken;

        String message = "Dear JavaQuizHub User,\n\n" +
                "It seems that  you forgot your password\n" +
                "Please use the following link to set a new password for your account:\n\n" +
                activationUrl + "\n\n" +
                "If you didn't initiate this action, you can safely ignore this message.\n\n" +
                "Best regards,\n" +
                "JavaQuizHub Team";

        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getUsername());
        email.setSubject("Reset Password");
        email.setText(message);

        mailSender.send(email);
    }

    @Override
    public void resendVerificationTokenLetter(String appUrl, String verificationToken, User user) {
        String confirmationUrl =
                appUrl + "/registrationConfirm?token=" + verificationToken;

        String message = "Dear JavaQuizHub User,\n\n" +
                "It appears that the verification token for your account has expired.\n" +
                "Please use the following link to obtain a new token and activate your account:\n\n" +
                confirmationUrl + "\n\n" +
                "If you didn't initiate this action, you can safely ignore this message.\n\n" +
                "Best regards,\n" +
                "JavaQuizHub Team";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message);
        email.setTo(user.getUsername());

        mailSender.send(email);
    }
}
