package com.example.javaquizhub.service;

import com.example.javaquizhub.model.Test;


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
