package com.example.javaquizhub.controller;

import com.example.javaquizhub.dto.TestSessionDTO;
import com.example.javaquizhub.model.Category;
import com.example.javaquizhub.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    String showBooksPage(Model model ){
        model.addAttribute("books",bookService.getAllBooks());
        return "books";

    }

    @GetMapping("/book/{bookId}")
    public String getBookById(@PathVariable("bookId") int bookId, Model model){
        model.addAttribute("book",bookService.getBookById(bookId));
        model.addAttribute("categories", Category.values());
        model.addAttribute(new TestSessionDTO());
        return "book-details";
    }
}
