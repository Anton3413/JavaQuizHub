package com.example.javaquizhub.repository;

import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer,Integer> {

    List<TestAnswer> getTestAnswersByTest_Id(int id);

    @Query(value = "SELECT * FROM answer WHERE question_id = :questionId AND is_correct = true", nativeQuery = true)
    List<TestAnswer> findCorrectAnswersByQuestionId(int questionId);

    List<TestAnswer> findTestAnswersByTest(Test test);
}

