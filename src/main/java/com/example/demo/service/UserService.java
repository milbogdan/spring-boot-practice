package com.example.demo.service;

import com.example.demo.exception.ExceptionBadRequest;
import com.example.demo.exception.ExceptionNotFound;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ExceptionNotFound("not found"));
    }

    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ExceptionBadRequest("User already exists");
        }
        userRepository.save(user);
    }
}
