package com.example.javaquizhub.service;

import com.example.javaquizhub.model.Category;
import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;

import java.util.List;

public interface TestService {

    Test getTestById(int id);

    List<Test> getAllTest();

    void deleteById(int id);

    List<Test> getTestsByBookAuthor(String author);

    List<Test> getTestsByBookTitle(String bookTitle);

  //  List<Test> getTestsByCategory(Category category);


}
