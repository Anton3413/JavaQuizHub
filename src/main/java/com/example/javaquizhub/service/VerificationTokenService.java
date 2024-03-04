package com.example.javaquizhub.service;

import com.example.javaquizhub.model.User;
import com.example.javaquizhub.model.VerificationToken;


public interface VerificationTokenService {

    VerificationToken getVerificationToken(String token);

    VerificationToken getTokenByUser(User user);
}
