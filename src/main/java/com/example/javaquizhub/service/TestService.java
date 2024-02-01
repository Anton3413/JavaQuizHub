package com.example.javaquizhub.service;

import com.example.javaquizhub.model.Category;
import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.model.TestAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestService {

    Test getTestById(int id);

    List<Test> getAllTest();

    void deleteById(int id);

    List<Test> getTestsByBookAuthor(String author);

    List<Test> getTestsByBookTitle(String bookTitle);

    List<Test> findTestsByBookIdAndCategoryInWithLimit( int bookId, List<String> categories, int limit);

    int countTestsByBookIdAndCategoryIn( int bookId, List<String> categories);

}
