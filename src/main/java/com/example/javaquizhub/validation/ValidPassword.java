package com.example.javaquizhub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Minimum 6 characters long and include lowercase, uppercase, digit  and special character, no spaces allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}