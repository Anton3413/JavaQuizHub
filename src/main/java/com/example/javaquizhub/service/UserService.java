package com.example.javaquizhub.service;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.model.User;
import com.example.javaquizhub.model.VerificationToken;

public interface UserService {

    User registerNewUserAccount(CreateUserDTO userDTO);
    boolean existsByUsername(String username);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

}
