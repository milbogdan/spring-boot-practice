package com.example.demo.controller;

import com.example.demo.DTOs.UserDTO;
import com.example.demo.mappers.UserMapper;
import com.example.demo.models.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/users")
@SecurityRequirement(name="bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/get")
    public List<UserDTO> getUsers() {

        return userMapper.userToUserDTOs(userService.getAll());
    }

    /*@PostMapping("/register")
    public void register(@RequestBody User user) {
        userService.registerUser(user);
    }*/
}
