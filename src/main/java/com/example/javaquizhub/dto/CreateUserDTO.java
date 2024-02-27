package com.example.javaquizhub.dto;

import com.example.javaquizhub.validation.PasswordMatches;
import com.example.javaquizhub.validation.UniqueUsername;
import com.example.javaquizhub.validation.ValidEmail;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@PasswordMatches
public class CreateUserDTO {

    @UniqueUsername
    @ValidEmail
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[A-Z]).{6,}$",
            message ="It must have a minimum length of 6 characters, contain at least one lowercase letter,one uppercase letter, and one digit.")
    private String rawPassword;

    private String matchingPassword;

}
