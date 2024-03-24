package com.example.javaquizhub.dto;


import com.example.javaquizhub.validation.PasswordMatches;
import com.example.javaquizhub.validation.ValidPassword;
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

    @ValidPassword
    String rawPassword;

    String matchingPassword;
}
