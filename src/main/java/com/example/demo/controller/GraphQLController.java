package com.example.demo.controller;

import com.example.demo.models.Listing;
import com.example.demo.models.User;
import com.example.demo.service.ListingService;
import com.example.demo.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLController {
    private final UserService userService;
    private final ListingService listingService;

    public GraphQLController(UserService userService, ListingService listingService) {
        this.userService = userService;
        this.listingService = listingService;
    }

    @QueryMapping
    public List<User> users(){
        return userService.getAll();
    }

    @QueryMapping
    public User userById(@Argument Long id){
        return userService.getById(id);
    }

    @QueryMapping
    public List<Listing> listings(){
        return listingService.findAllNoFilter();
    }

    @QueryMapping
    public Listing listingById(@Argument Long id){
        return listingService.getById(id);
    }


}
