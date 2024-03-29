package com.example.javaquizhub.repository;

import com.example.javaquizhub.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

        Optional<Book> findBookByAuthor(String author);
}
