package com.example.javaquizhub.controller;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.event.event.OnRegistrationCompleteEvent;
import com.example.javaquizhub.model.User;
import com.example.javaquizhub.service.UserService;
import com.example.javaquizhub.validation.EmailExistInApp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.javaquizhub.model.VerificationToken;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final JavaMailSender mailSender;

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        return "login-page";
    }

    @GetMapping("/logout")
    String showLogoutPage() {
        return "exit-page";
    }

    @GetMapping("/registration")
    String showRegistrationPage(Model model) {
        model.addAttribute("user", new CreateUserDTO());
        return "registration-page";
    }

    @PostMapping("/registration")
    String registerUserAccount(@ModelAttribute("user") @Valid CreateUserDTO createUserDTO,
                               BindingResult result, HttpServletRequest httpServletRequest) {

        if (result.hasErrors()) {
            return "registration-page";
        }
        User user = userService.registerNewUserAccount(createUserDTO);
        String contextPathAppUrl = httpServletRequest.getContextPath();

        publisher.publishEvent(new OnRegistrationCompleteEvent(user, contextPathAppUrl));
        return "login-page";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token, Model model) {

        VerificationToken verificationToken = userService.getVerificationToken(token);

        User user = verificationToken.getUser();

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            model.addAttribute("message", "Sorry but it looks like this token has expired");
            return "401-error-page";
        }
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        model.addAttribute("message", "Your account has been successfully activated");
        return "login-page";
    }

    @GetMapping("/resendActivationToken")
    @ResponseBody
    public void resendVerificationToken(HttpServletRequest request,
                                        @RequestParam("token") String existingToken) {

        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);

        User user = userService.getUser(newToken.getToken());
        String appUrl = "http://" + request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath();

        SimpleMailMessage message = constructResendVerificationTokenEmail(appUrl,newToken,user);

        mailSender.send(message);
    }
    @GetMapping("/user/forgotPassword")
    public String getForgotPasswordPage(){
        return "forgot-password-page";
    }

    @PostMapping("/user/forgotPassword")
    public String handleEmailForResetPasswordToken(@RequestParam("email") @EmailExistInApp String email,
                                                       BindingResult result,HttpServletRequest request,
                                                   Model model ){

        if(result.hasErrors()){
            return "forgot-password-page";
        }

        User user = userService.findByUsername(email);

        String resetToken = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user,resetToken);

        String appUrl = "http://" + request.getServerName() +
                ":" + request.getServerPort() +
                request.getContextPath() + "/changePassword";

        SimpleMailMessage message = constructChangePasswordEmail(appUrl,resetToken,user);

        mailSender.send(message);
        String text = "Follow the instructions sent to your email to change your password";

        model.addAttribute("message",text);
        return "login-page";
    }

    @GetMapping("/user/changePassword")
    public String getChangePasswordPage(@RequestParam(name = "token",required = false) String token){


    }



    private SimpleMailMessage constructChangePasswordEmail(final String appUrl, final String token, final User user) {
        final String activationUrl = appUrl+ "?token="+ token;
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
        return email;
    }

    private SimpleMailMessage constructResendVerificationTokenEmail
            (String appUrl, VerificationToken newToken, User user) {

        String confirmationUrl =
                appUrl + "/registrationConfirm?token=" + newToken.getToken();

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

        return email;
    }

}
