package com.example.javaquizhub.service.impl;

import com.example.javaquizhub.model.Book;
import com.example.javaquizhub.repository.BookRepository;
import com.example.javaquizhub.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;


    @Override
    public Book getBookById(int id) {
       return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Book getBookByAuthor(String author) {
       return bookRepository.findBookByAuthor(author).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book create(Book book) {
       return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
      return  bookRepository.save(book);
    }

    @Override
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }
}
