package com.example.javaquizhub.service;


import com.example.javaquizhub.model.User;


public interface MailService {

     void sendForgotPasswordTokenLetter(String appUrl, String changePasswordToken,User user);

     void resendVerificationTokenLetter(String appUrl, String verificationToken, User user);

}
