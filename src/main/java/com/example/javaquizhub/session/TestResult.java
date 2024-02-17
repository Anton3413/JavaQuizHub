package com.example.javaquizhub.session;

import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TestResult {

    Test test;

    List<TestAnswer> userAnswers;

    List<TestAnswer> correctAnswers;
}
