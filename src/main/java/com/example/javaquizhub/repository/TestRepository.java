package com.example.javaquizhub.repository;

import com.example.javaquizhub.model.Category;
import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {

    List<Test> getTestsByBookAuthor(String author);

    List<Test> getTestsByBookTitle(String bookTitle);

  //  List<Test> getTestsBycA

}
