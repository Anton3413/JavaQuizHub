package com.example.javaquizhub.controller;

import com.example.javaquizhub.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TestController {

    @Autowired
    BookService bookService;
    @GetMapping("/")
    String openMainPage(){
        return "main-page";
    }

    @GetMapping("/books")
    String showBooksPage(Model model){
        model.addAttribute("books",bookService.getAllBooks());
        return "books-page";
    }

    @GetMapping("/book/{bookId}")
    public String getBookById(@PathVariable("bookId") int bookId, Model model ){

        return null;
    }
}
