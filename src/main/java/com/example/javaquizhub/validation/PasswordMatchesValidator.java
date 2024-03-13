package com.example.javaquizhub.validation;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.dto.PasswordHolder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordHolder> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(PasswordHolder holder, ConstraintValidatorContext context){

        return holder.getRawPassword().equals(holder.getMatchingPassword());
    }
}