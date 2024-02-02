package com.example.javaquizhub.model;

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
