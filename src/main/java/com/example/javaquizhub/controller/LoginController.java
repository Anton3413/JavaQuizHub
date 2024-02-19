package com.example.javaquizhub.controller;

import com.example.javaquizhub.model.User;
import com.example.javaquizhub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

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
        model.addAttribute("user",new User());
        return "registration-page";
    }

   /* @PostMapping("/registration")
    String handleRegistrationForm(@ModelAttribute("user") @Valid User user,
                                  BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors",result.getAllErrors());
            return "redirect:/registration";
        }

        userService.create(user);
        return "redirect:/login";
    }*/
}
