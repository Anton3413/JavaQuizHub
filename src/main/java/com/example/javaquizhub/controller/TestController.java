package com.example.javaquizhub.controller;

import com.example.javaquizhub.service.BookService;
import com.example.javaquizhub.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    TestService testService;

    @PostMapping("/book/{bookId}/start")
    public String startTesting(@PathVariable("bookId") int bookId, @RequestParam("numberOfTests") int number,
                               @RequestParam("categories") List<String> testCategories){
        System.out.println(testCategories);


        return "ttt";
    }
}
