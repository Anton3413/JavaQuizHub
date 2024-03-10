package com.example.javaquizhub.service.impl;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.exception.custom_exceptions.TokenException;
import com.example.javaquizhub.mapper.CreateUserDTOMapper;
import com.example.javaquizhub.model.PasswordResetToken;
import com.example.javaquizhub.model.User;
import com.example.javaquizhub.model.VerificationToken;
import com.example.javaquizhub.repository.PasswordResetTokenRepository;
import com.example.javaquizhub.repository.UserRepository;
import com.example.javaquizhub.repository.VerificationTokenRepository;
import com.example.javaquizhub.service.UserService;
import com.example.javaquizhub.service.VerificationTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService, VerificationTokenService {

    private final UserRepository userRepository;
    private final CreateUserDTOMapper createUserDTOMapper;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        User user = userRepository.findByUsername(username).get();

        if(user==null){
            throw  new UsernameNotFoundException("Unable to login. The email or password is incorrect");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                Collections.singleton(user.getRole()));
    }

    public User findByUsername(String username){
       return userRepository.findByUsername(username)
               .orElseThrow(()->new EntityNotFoundException("User not found!"));
    }

    @Override
    public User registerNewUserAccount(CreateUserDTO userDTO) {
        User user = createUserDTOMapper.map(userDTO);
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username){
       return userRepository.existsByUsername(username);
    }

    public User getUser(String verificationToken){
        return tokenRepository.findByToken(verificationToken)
                .map(VerificationToken::getUser)
                .orElseThrow(() -> new TokenException("token " + verificationToken +
                        "not found or user with such token does not exist"));
    }
     public VerificationToken getVerificationToken(String token){
        return tokenRepository.findByToken(token)
                        .orElseThrow( () -> new TokenException("Oops! It seems that such a token does not exist"));
    }
    public VerificationToken getTokenByUser(User user){
        return tokenRepository.findByUser(user)
                .orElseThrow( () -> new TokenException("Token for this user is not found.\n" +
                        "(Likely, this user does not exist.)"));
    }
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setToken(token);
        verificationToken.setUser(user);

        tokenRepository.save(verificationToken);
    }
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken token = tokenRepository.findByToken(existingVerificationToken)
                .orElseThrow(() ->new TokenException("Token not found"));
        token.updateToken(UUID.randomUUID().toString());
        token = tokenRepository.save(token);
        return token;
    }

    public void createPasswordResetTokenForUser(User user, String token){
        PasswordResetToken resetToken = new PasswordResetToken(user,token);

        passwordResetTokenRepository.save(resetToken);

    }

    public PasswordResetToken getPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }

    public User getUserByPasswordResetToken(String token){

       return passwordResetTokenRepository.findByToken(token).getUser();
    }
}