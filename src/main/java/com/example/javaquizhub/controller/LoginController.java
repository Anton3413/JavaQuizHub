package com.example.javaquizhub.controller;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.event.event.OnRegistrationCompleteEvent;
import com.example.javaquizhub.model.User;
import com.example.javaquizhub.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import com.example.javaquizhub.model.VerificationToken;

import java.time.LocalDateTime;
import java.util.Calendar;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @GetMapping("/login")
    String showLoginPage(){
        return "login-page";
    }

    @GetMapping("/logout")
    String showLogoutPage(){
        return "exit-page";
    }

    @GetMapping("/registration")
    String showRegistrationPage(Model model){
        model.addAttribute("user", new CreateUserDTO());
        return "registration-page";
    }
    @PostMapping("/registration")
    String registerUserAccount(@ModelAttribute("user") @Valid CreateUserDTO createUserDTO,
                               HttpServletRequest httpServletRequest, BindingResult result,
                               Model model){

        if(result.hasErrors()){
            return "registration-page";
        }

        User user = userService.registerNewUserAccount(createUserDTO);
        String contextPathAppUrl = httpServletRequest.getContextPath();

        publisher.publishEvent(new OnRegistrationCompleteEvent(user,contextPathAppUrl));
        return "redirect:/login";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
           /* String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();*/
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();


        if(verificationToken.getExpiryDate().isAfter(LocalDateTime.now())) {
           /* model.addAttribute("message", messageValue);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();*/
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:/login";
    }
}
