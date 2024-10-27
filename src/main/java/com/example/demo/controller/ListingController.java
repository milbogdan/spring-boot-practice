package com.example.demo.controller;

import com.example.demo.DTOs.ListingDTO;
import com.example.demo.exception.ExceptionBadRequest;
import com.example.demo.models.Listing;
import com.example.demo.models.User;
import com.example.demo.service.ListingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/listing")
@SecurityRequirement(name="bearerAuth")
public class ListingController {
    private final ListingService listingService;
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }
    @GetMapping("/getAll/{page}/{size}")
    public List<Listing> getListings(@PathVariable int page, @PathVariable int size, @RequestParam(required = false) String search) {
        return listingService.findAll(search, page, size);
    }
    @GetMapping("/getAllByUser/{page}/{size}")
    public List<Listing> getListingsForUser(@PathVariable int page, @PathVariable int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User newUser = (User) authentication.getPrincipal();
        return listingService.findAllForUser(newUser.getId(), page, size);
    }

    @GetMapping("/getAllDeactivatedByUser")
    public List<Listing> getDeactivatedListingsForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User newUser = (User) authentication.getPrincipal();
        return listingService.findAllDeactivatedForUser(newUser.getId());
    }

    @PostMapping("/save")
    public Listing saveListing(@RequestBody ListingDTO listingDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User newUser = (User) authentication.getPrincipal();
        return listingService.createListing(listingDTO.getTitle(),listingDTO.getDescription(),newUser.getId());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteListing(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User newUser = (User) authentication.getPrincipal();
        listingService.deleteListing(newUser.getId(),id);
    }

    @PutMapping("/update/{id}")
    public Listing updateListing(@PathVariable Long id, @RequestBody ListingDTO listing) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User newUser = (User) authentication.getPrincipal();
        Listing updatedListing = listingService.updateListing(newUser.getId(), id,listing);
        return updatedListing;
    }

    @PutMapping("/deactivate/{id}")
    public void deactivateListing(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User newUser = (User) authentication.getPrincipal();
        listingService.deactivateListing(newUser.getId(),id);
    }

    @PutMapping("/activate/{id}")
    public void activateListing(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User newUser = (User) authentication.getPrincipal();
        listingService.activateListing(newUser.getId(),id);
    }

    //Endpoint that calls async method, experimenting purposes
    @GetMapping("/callAsync")
    public CompletableFuture<String> callAsync() throws InterruptedException {
        CompletableFuture<String> string = new CompletableFuture<>();
        string=listingService.asyncFunction();
        System.out.println("PRINT AFTER THE ASYNC METHOD CALL");
        return string;
    }
}
