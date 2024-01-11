package com.example.javaquizhub.repository;

import com.example.javaquizhub.model.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer,Integer> {
}
