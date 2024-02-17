package com.example.javaquizhub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = TestSessionDTOValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestSessionDTOValidation {

    String message() default "There aren't that many tests in this book.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
