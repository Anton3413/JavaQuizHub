package com.example.javaquizhub.repository;

import com.example.javaquizhub.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer> {
}
