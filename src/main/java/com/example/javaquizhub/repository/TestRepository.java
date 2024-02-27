package com.example.javaquizhub.repository;

import com.example.javaquizhub.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {

    List<Test> getTestsByBookAuthor(String author);

    List<Test> getTestsByBookTitle(String bookTitle);

    @Query(value = "SELECT * FROM test WHERE book_id = :bookId AND category IN (:categories) LIMIT :limit", nativeQuery = true)
    List<Test> findTestsByBookIdAndCategoryInWithLimit(@Param("bookId") int bookId,
                                                       @Param("categories") List<String> categories,
                                                       @Param("limit") int limit);

    @Query(value = "SELECT COUNT(*) FROM test WHERE book_id = :bookId AND category IN (:categories)", nativeQuery = true)
    int countTestsByBookIdAndCategoryIn(@Param("bookId") int bookId, @Param("categories") List<String> categories);


}
