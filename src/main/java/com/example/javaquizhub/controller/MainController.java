package com.example.javaquizhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    String openMainPage(){
        return "main-page";
    }
}
