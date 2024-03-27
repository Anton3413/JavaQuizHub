package com.example.javaquizhub.service.impl;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.dto.PasswordDTO;
import com.example.javaquizhub.exception.custom_exceptions.TokenException;
import com.example.javaquizhub.mapper.CreateUserDTOMapper;
import com.example.javaquizhub.model.PasswordResetToken;
import com.example.javaquizhub.model.Role;
import com.example.javaquizhub.model.User;
import com.example.javaquizhub.model.VerificationToken;
import com.example.javaquizhub.repository.PasswordResetTokenRepository;
import com.example.javaquizhub.repository.UserRepository;
import com.example.javaquizhub.repository.VerificationTokenRepository;
import com.example.javaquizhub.service.PasswordResetTokenService;
import com.example.javaquizhub.service.UserService;
import com.example.javaquizhub.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService, VerificationTokenService,
                                                                    PasswordResetTokenService {

    private final UserRepository userRepository;
    private final CreateUserDTOMapper createUserDTOMapper;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        User user = userRepository.findByUsername(username);

        if(user==null){
            throw  new BadCredentialsException("Unable to login. The email or password is incorrect");
        }

        if (!user.isEnabled()) {
            throw new DisabledException("To activate your account, follow the instructions sent to you by email.");
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
       return userRepository.findByUsername(username);
    }

    @Override
    public User registerNewUserAccount(CreateUserDTO userDTO) {
        User user = createUserDTOMapper.map(userDTO);
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username){
       return userRepository.existsByUsername(username);
    }

    @Override
    public User registerOauthUser(String email) {
        User user = new User();
        user.setUsername(email);
        user.setRole(Role.USER);
       return userRepository.save(user);
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

    public String createPasswordResetTokenForUser(User user){

        PasswordResetToken oldToken = passwordResetTokenRepository.findByUser(user);

        String newGeneratedResetToken = UUID.randomUUID().toString();

        if(oldToken==null){
            PasswordResetToken newToken = new PasswordResetToken(user,newGeneratedResetToken);
            passwordResetTokenRepository.save(newToken);
        }
        else{
            oldToken.updateToken(newGeneratedResetToken);
            passwordResetTokenRepository.save(oldToken);
        }
        return newGeneratedResetToken;
    }

    public PasswordResetToken getPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }

    public User getUserByPasswordResetToken(String token){

       return passwordResetTokenRepository.findByToken(token).getUser();
    }


    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        if(passToken == null){
            return "Token is not found";
        }

        if(!isTokenExpired(passToken)){
            return "Sorry but it looks like this token has expired";
        }
        return "valid";
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {

        return passToken.getExpiryDate().isAfter(LocalDateTime.now());
    }
    public void changeUserPassword(PasswordDTO passwordDTO){
        User user = getUserByPasswordResetToken(passwordDTO.getToken());

        user.setPassword(passwordEncoder.encode(passwordDTO.getRawPassword()));

        userRepository.save(user);
    }
    public void deleteVerificationToken(VerificationToken token){
        tokenRepository.delete(token);
    }

    public void deleteResetPasswordToken(PasswordResetToken token){
        passwordResetTokenRepository.delete(token);
    }
}