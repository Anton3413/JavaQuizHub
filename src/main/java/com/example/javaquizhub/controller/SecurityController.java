package com.example.javaquizhub.controller;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.dto.PasswordDTO;
import com.example.javaquizhub.event.event.OnRegistrationCompleteEvent;
import com.example.javaquizhub.model.User;
import com.example.javaquizhub.model.VerificationToken;
import com.example.javaquizhub.service.MailService;
import com.example.javaquizhub.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final MailService mailService;
    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false)String error, HttpServletRequest request, Model model)  {
        if(error!=null){
            AuthenticationException exception = (AuthenticationException) request.getSession()
                    .getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            model.addAttribute("errorMessage",exception.getMessage());
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
                               BindingResult result, HttpServletRequest httpServletRequest,
                               Model model) {

        if (result.hasErrors()) {
            return "registration-page";
        }
        User user = userService.registerNewUserAccount(createUserDTO);
        String contextPathAppUrl = httpServletRequest.getContextPath();

        publisher.publishEvent(new OnRegistrationCompleteEvent(user, contextPathAppUrl));

        model.addAttribute("message",
                "To complete registration, follow the instructions sent to: " +user.getUsername());
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
        userService.deleteVerificationToken(verificationToken);
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

        mailService.resendVerificationTokenLetter(appUrl,newToken.getToken(),user);
    }

    @GetMapping("/user/forgotPassword")
    public String getForgotPasswordPage(){
        return "forgot-password-page";
    }

    @PostMapping("/user/forgotPassword")
    public String handleEmailForResetPasswordToken(@ModelAttribute("email") String email,HttpServletRequest request,
                                                   Model model ){

        User user = userService.findByUsername(email);
        if(user==null){
            model.addAttribute("emailError",
                    "A user with this email was not found");
            return "forgot-password-page";
        }

        String newGeneratedToken =  userService.createPasswordResetTokenForUser(user);

        String appUrl = "http://" + request.getServerName() +
                ":" + request.getServerPort() +
                "/user/changePassword";

        mailService.sendForgotPasswordTokenLetter(appUrl,newGeneratedToken,user);

        model.addAttribute("message",
                "Follow the instructions sent to your email to change your password");
        return "login-page";
    }

    @GetMapping("/user/changePassword")
    public String getChangePasswordPage(@RequestParam(name = "token") String token, Model model){

        String validationResult = userService.validatePasswordResetToken(token);

        if(!validationResult.equals("valid")){
            model.addAttribute("message",validationResult);
            return "login-page";
        }
        return "redirect:/user/updatePassword?token=" + token;
    }

    @GetMapping("/user/updatePassword")
    public String showUpdatePasswordPage(@RequestParam(name = "token",required = true) String token,Model model){

        PasswordDTO passwordDTO = new PasswordDTO(token);

        model.addAttribute("passwordDTO",passwordDTO);

        return "update-password-page";
    }

    @PostMapping("/user/updatePassword")
    public String handleNewPasswordDTO(@Valid PasswordDTO passwordDTO,
                                       BindingResult result,
                                       Model model){
        if(result.hasErrors()){
            return "update-password-page";
        }
        String validationResult = userService.validatePasswordResetToken(passwordDTO.getToken());
        if(!validationResult.equals("valid")){
            model.addAttribute("message",validationResult);
            return "login-page";
        }
        userService.changeUserPassword(passwordDTO);
        model.addAttribute("message","Your password has been successfully changed");
        return "login-page";
    }
}
