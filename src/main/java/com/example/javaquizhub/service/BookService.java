package com.example.javaquizhub.service;

import com.example.javaquizhub.model.Book;

import java.util.List;

public interface BookService{

    Book getBookById(int id);

    Book getBookByAuthor(String author);

    List<Book> getAllBooks();

    Book create(Book book);

    Book update(Book book);

    void deleteById(int id);

}
