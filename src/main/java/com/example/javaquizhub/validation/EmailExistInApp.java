package com.example.javaquizhub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface EmailExistInApp {
    String message() default "A user with this email was not found. Enter the email you provided during registration.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}