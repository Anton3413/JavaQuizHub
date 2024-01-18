package com.example.javaquizhub.service.impl;

import com.example.javaquizhub.model.Category;
import com.example.javaquizhub.model.Test;
import com.example.javaquizhub.repository.TestRepository;
import com.example.javaquizhub.service.TestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testRepository;
    @Override
    public Test getTestById(int id) {
        return testRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Test> getAllTest() {
        return testRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        testRepository.deleteById(id);
    }

    @Override
    public List<Test> getTestsByBookAuthor(String author) {
       return testRepository.getTestsByBookAuthor(author);
    }

    @Override
    public List<Test> getTestsByBookTitle(String bookTitle) {
       return testRepository.getTestsByBookTitle(bookTitle);
    }

   // @Override
  //  public List<Test> getTestsByCategory(Category category) {
   //    return testRepository.getTestsByCategory(category);
   // }
}
