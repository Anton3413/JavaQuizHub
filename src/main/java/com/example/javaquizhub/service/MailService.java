package com.example.javaquizhub.service;


import com.example.javaquizhub.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


public interface MailService {

     void sendForgotPasswordTokenLetter(String appUrl, String changePasswordToken,User user);

     void resendVerificationTokenLetter(String appUrl, String verificationToken, User user);

     void sendRegistrationTokenLetter(String confirmationUrl, String email);

}
