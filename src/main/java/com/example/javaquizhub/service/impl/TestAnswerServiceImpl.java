package com.example.javaquizhub.service.impl;

import com.example.javaquizhub.model.TestAnswer;
import com.example.javaquizhub.repository.TestAnswerRepository;
import com.example.javaquizhub.service.TestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestAnswerServiceImpl implements TestAnswerService {

    @Autowired
    TestAnswerRepository testAnswerRepository;
    @Override
    public List<TestAnswer> getTestAnswersByTest_Id(int id) {
        return testAnswerRepository.getTestAnswersByTest_Id(id);
    }

    @Override
    public List<TestAnswer> findCorrectAnswersByQuestionId(int questionId) {
        return testAnswerRepository.findCorrectAnswersByQuestionId(questionId);
    }
}
