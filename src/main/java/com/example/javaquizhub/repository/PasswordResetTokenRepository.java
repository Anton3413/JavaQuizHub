package com.example.javaquizhub.repository;

import com.example.javaquizhub.model.PasswordResetToken;
import com.example.javaquizhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Integer> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
}
