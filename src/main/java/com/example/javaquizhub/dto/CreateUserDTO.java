package com.example.javaquizhub.dto;

import com.example.javaquizhub.validation.PasswordMatches;
import com.example.javaquizhub.validation.UniqueUsername;
import com.example.javaquizhub.validation.ValidEmail;
import com.example.javaquizhub.validation.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@PasswordMatches
public class CreateUserDTO implements PasswordHolder {

    @UniqueUsername
    @ValidEmail
    private String username;

    @ValidPassword
    private String rawPassword;

    private String matchingPassword;

}
