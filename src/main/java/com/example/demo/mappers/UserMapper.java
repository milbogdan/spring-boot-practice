package com.example.demo.mappers;

import com.example.demo.DTOs.UserDTO;
import com.example.demo.models.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDTO> userToUserDTOs(List<User> users);
}
