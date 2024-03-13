package com.example.javaquizhub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailExistInAppValidator.class)
@Documented
public @interface EmailExistInApp {
    String message() default "A user with this email was not found. Enter the email you provided during registration";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}