package com.example.javaquizhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    String showLoginPage(){
        return "login-page";
    }

    @GetMapping("/logout")
    String showLogOutPage(){
        return "exit-page";
    }
}
