package com.example.javaquizhub.service.impl;

import com.example.javaquizhub.repository.UserRepository;
import com.example.javaquizhub.service.UserService;
import lombok.RequiredArgsConstructor;
/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;*/
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService/*UserDetailsService*/ {

    private final UserRepository userRepository;

   /* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())))
               .orElseThrow(()->new UsernameNotFoundException("Failed to retrieve user" + username));
    }

    @Override
    public void create() {

    }*/
}
