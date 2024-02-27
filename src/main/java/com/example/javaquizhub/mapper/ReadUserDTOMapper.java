package com.example.javaquizhub.mapper;

import com.example.javaquizhub.dto.ReadUserDTO;
import com.example.javaquizhub.model.User;

public class ReadUserDTOMapper implements  Mapper<User, ReadUserDTO> {

    @Override
    public ReadUserDTO map(User user) {
        ReadUserDTO readUserDTO = new ReadUserDTO();

        readUserDTO.setId(user.getId());
        readUserDTO.setUsername(user.getUsername());

        return readUserDTO;
    }
}
