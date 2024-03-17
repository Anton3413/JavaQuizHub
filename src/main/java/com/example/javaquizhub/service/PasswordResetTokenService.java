package com.example.javaquizhub.service;

import com.example.javaquizhub.model.PasswordResetToken;
import com.example.javaquizhub.model.User;

public interface PasswordResetTokenService {

    String createPasswordResetTokenForUser(User user);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);
}
