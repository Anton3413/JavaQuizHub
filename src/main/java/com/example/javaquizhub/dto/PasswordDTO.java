package com.example.javaquizhub.dto;


import com.example.javaquizhub.validation.PasswordMatches;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class PasswordDTO implements PasswordHolder {

    public PasswordDTO(String token){
        this.token = token;
    }

    String token;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[A-Z]).{6,}$",
            message ="Minimum 6 characters long and include lowercase, uppercase, and digit")
    String rawPassword;

    String matchingPassword;
}
