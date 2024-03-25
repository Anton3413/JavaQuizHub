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

        String text = "Dear JavaQuizHub User,\n\n" +
                "It seems that  you forgot your password\n" +
                "Please use the following link to set a new password for your account:\n\n" +
                activationUrl + "\n\n" +
                "If you didn't initiate this action, you can safely ignore this message.\n\n" +
                "Best regards,\n" +
                "JavaQuizHub Team";

        sendMessage(user.getUsername(),"Reset Password",text);
    }

    @Override
    public void resendVerificationTokenLetter(String appUrl, String verificationToken, User user) {
        String confirmationUrl =
                appUrl + "/registrationConfirm?token=" + verificationToken;

        String text = "Dear JavaQuizHub User,\n\n" +
                "It appears that the verification token for your account has expired.\n" +
                "Please use the following link to obtain a new token and activate your account:\n\n" +
                confirmationUrl + "\n\n" +
                "If you didn't initiate this action, you can safely ignore this message.\n\n" +
                "Best regards,\n" +
                "JavaQuizHub Team";

        sendMessage(user.getUsername(),"Resend Registration Token",text);
    }

    @Override
    public void sendRegistrationTokenLetter(String confirmationUrl, String email) {
        final String text = "Thank you for registering with JavaQuizHub!\n\n"
                + "To complete your registration, please click on the following link:\n"
                + "\n" +  confirmationUrl + "\n"
                + "If you did not register on JavaQuizHub, please ignore this email.\n\n"
                + "Best regards,\n"
                + "The JavaQuizHub Team";

        sendMessage(email,"Registration",text);
    }

    private void sendMessage(String to, String subject, String text){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("JavaQuizHub");
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
