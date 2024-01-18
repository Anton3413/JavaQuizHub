package com.example.javaquizhub.controller;

import com.example.javaquizhub.model.Category;
import com.example.javaquizhub.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;

@Controller
public class BookController {

    @Autowired
    BookService bookService;
    @GetMapping("/books")
    String showBooksPage(Model model){
        model.addAttribute("books",bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/book/{bookId}")
    public String getBookById(@PathVariable("bookId") int bookId, Model model){
        model.addAttribute("book",bookService.getBookById(bookId));
        model.addAttribute("categories", Category.values());
        System.out.println(Arrays.toString(Category.values()));
        return "book-details";
    }
}
