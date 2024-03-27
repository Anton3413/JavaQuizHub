package com.example.javaquizhub.service;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.dto.PasswordDTO;
import com.example.javaquizhub.model.PasswordResetToken;
import com.example.javaquizhub.model.User;
import com.example.javaquizhub.model.VerificationToken;

import java.util.Optional;

public interface UserService {

    User registerNewUserAccount(CreateUserDTO userDTO);

    boolean existsByUsername(String username);

    User registerOauthUser(String email);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(final String existingVerificationToken);

    User findByUsername(String username);

    String createPasswordResetTokenForUser(User user);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

     String validatePasswordResetToken(String token);

    void changeUserPassword(PasswordDTO passwordDTO);

    void deleteVerificationToken(VerificationToken token);

    void deleteResetPasswordToken(PasswordResetToken token);

}
