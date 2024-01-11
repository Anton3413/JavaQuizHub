package com.example.javaquizhub.controller;

import com.example.javaquizhub.model.Book;
import com.example.javaquizhub.service.BookService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstController {

    @Autowired
    BookService bookService;
    @RequestMapping("/")
    String openMainPage(){
        System.out.println("Hello world!");

        Book book = new Book();

        book.setTitle("Example Title");
        book.setAuthor("Example Author");

      //  bookService.create(book);
        return "Hello world";
    }
}
