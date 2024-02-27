package com.example.javaquizhub.service;

import com.example.javaquizhub.dto.CreateUserDTO;

public interface UserService {

    void createUser(CreateUserDTO userDTO);
    boolean existsByUsername(String username);

}
