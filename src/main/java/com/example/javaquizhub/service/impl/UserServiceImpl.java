package com.example.javaquizhub.service.impl;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.exception.AccountNotActivatedException;
import com.example.javaquizhub.mapper.CreateUserDTOMapper;
import com.example.javaquizhub.model.User;
import com.example.javaquizhub.model.VerificationToken;
import com.example.javaquizhub.repository.UserRepository;
import com.example.javaquizhub.repository.VerificationTokenRepository;
import com.example.javaquizhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final CreateUserDTOMapper createUserDTOMapper;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        User user = userRepository.findByUsername(username);

        if(user==null){
            throw  new UsernameNotFoundException("No user found with username: " + username);
        }
        if(!user.isEnabled()){
            throw new AccountNotActivatedException("Account has not been activated");
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

    @Override
    public User registerNewUserAccount(CreateUserDTO userDTO) {
        User user = createUserDTOMapper.map(userDTO);
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username){
       return userRepository.existsByUsername(username);
    }

    public User getUser(String verificationToken){
       return tokenRepository.findByToken(verificationToken).get().getUser();
    }

     public VerificationToken getVerificationToken(String token){
        return tokenRepository.findByToken(token).orElseThrow(RuntimeException::new);
    }

    VerificationToken getTokenByUser(User user){
        return tokenRepository.findByUser(user).orElseThrow(RuntimeException::new);
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
}
