package com.example.javaquizhub.controller;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("user", new CreateUserDTO());
        return "registration-page";
    }
    @PostMapping("/registration")
    String registerUserAccount(@ModelAttribute("user") @Valid CreateUserDTO createUserDTO,
                                  BindingResult result, Model model){

        if(result.hasErrors()){
            return "registration-page";
        }

        userService.createUser(createUserDTO);
        return "redirect:/login";
    }
}
