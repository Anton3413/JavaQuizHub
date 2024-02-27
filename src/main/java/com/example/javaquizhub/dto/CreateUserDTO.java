package com.example.javaquizhub.dto;

import com.example.javaquizhub.validation.UniqueUsername;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CreateUserDTO {

    @UniqueUsername
    @Email(message = "Invalid email")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)[a-zA-Z\\\\d]{6,}$",
            message ="Invalid password. It must have a minimum length of 6 characters, contain at least one lowercase letter, at least one uppercase letter, and at least one digit." )
    private String rawPassword;

}
