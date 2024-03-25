package com.example.javaquizhub.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/user/myProfile")
    @PreAuthorize("isAuthenticated()")
    public String showUserPage(@AuthenticationPrincipal UserDetails user){

        return "user-page";
    }

}
