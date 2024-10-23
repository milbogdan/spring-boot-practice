package com.example.demo.controller;

import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public List<User> getUsers() {
        return userService.getAll();
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        userService.registerUser(user);
    }
}
