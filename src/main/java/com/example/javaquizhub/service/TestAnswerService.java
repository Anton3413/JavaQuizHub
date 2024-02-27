package com.example.javaquizhub.service;

import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;

import java.util.List;

public interface TestAnswerService  {

    List<TestAnswer> getTestAnswersByTest_Id(int id);

    List<TestAnswer> findCorrectAnswersByQuestionId(int questionId);

    List<TestAnswer> findTestAnswersByTest(Test test);
}
