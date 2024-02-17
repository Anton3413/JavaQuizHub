package com.example.javaquizhub.validation;

import com.example.javaquizhub.dto.TestSessionDTO;
import com.example.javaquizhub.service.TestService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestSessionDTOValidator implements ConstraintValidator<TestSessionDTOValidation, TestSessionDTO> {

    private final TestService testService;
    @Override
    public void initialize(TestSessionDTOValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TestSessionDTO testSessionDTO, ConstraintValidatorContext constraintValidatorContext) {

        int requiredNumberOfTest = testSessionDTO.getNumberOfTests();

        int countOfTestsInDatabase = testService.
                countTestsByBookIdAndCategoryIn(testSessionDTO.getBookId(),testSessionDTO.getTestCategories());

        return countOfTestsInDatabase>=requiredNumberOfTest;
    }
}
