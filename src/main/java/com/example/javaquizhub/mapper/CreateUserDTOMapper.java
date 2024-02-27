package com.example.javaquizhub.mapper;

import com.example.javaquizhub.dto.CreateUserDTO;
import com.example.javaquizhub.model.Role;
import com.example.javaquizhub.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserDTOMapper implements Mapper<CreateUserDTO,User> {

   private final PasswordEncoder passwordEncoder;
   public User map(CreateUserDTO userDTO){
       User user = new User();

       user.setUsername(userDTO.getUsername());
       user.setRole(Role.USER);
       user.setPassword(passwordEncoder.encode(userDTO.getRawPassword()));
       return user;
   }

}
