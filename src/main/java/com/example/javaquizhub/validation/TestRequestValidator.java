package com.example.javaquizhub.validation;

import com.example.javaquizhub.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;


public class TestRequestValidator {

    TestService testService;

    public TestRequestValidator(TestService testService) {
        this.testService = testService;
    }


    public BindingResult validate(int bookId, int numberOfTests, List<String> testCategories){

        BindingResult result = new BeanPropertyBindingResult(new Object(), "numberOfTests");
        if(numberOfTests <= 0){
            result.addError( new ObjectError("numberOfTests", "Must be greater than 0"));
        }
        int countOfTestsInDatabase = testService.countTestsByBookIdAndCategoryIn(bookId,testCategories);

        if(numberOfTests>countOfTestsInDatabase){
            result.addError(new ObjectError("numberOfTests", "There's not much in this book"));
        }
        return result;
    }
}
