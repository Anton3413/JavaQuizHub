package com.example.javaquizhub.event.listener;

import com.example.javaquizhub.event.event.OnRegistrationCompleteEvent;
import com.example.javaquizhub.model.User;
import com.example.javaquizhub.service.MailService;
import com.example.javaquizhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RegistrationListener  implements ApplicationListener<OnRegistrationCompleteEvent> {

   private final UserService userService;
   private final MailService mailService;
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.completeRegistration(event);
    }

    private void completeRegistration(OnRegistrationCompleteEvent event){
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user,token);

        String email = user.getUsername();
        String confirmationUrl = "http://localhost:8080" + event.getContextPathAppUrl() +
                "/registrationConfirm?token=" + token;

        mailService.sendRegistrationTokenLetter(confirmationUrl,email);
    }
}
