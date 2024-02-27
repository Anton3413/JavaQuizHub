package com.example.javaquizhub.validation;

import com.example.javaquizhub.dto.CreateUserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CreateUserDTO user = (CreateUserDTO) obj;
        return user.getRawPassword().equals(user.getMatchingPassword());
    }
}