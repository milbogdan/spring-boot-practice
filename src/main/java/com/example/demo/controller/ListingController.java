package com.example.demo.controller;

import com.example.demo.DTOs.ListingDTO;
import com.example.demo.exception.ExceptionBadRequest;
import com.example.demo.models.Listing;
import com.example.demo.service.ListingService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/listing")
public class ListingController {
    private final ListingService listingService;
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/getAll/{userId}")
    public List<Listing> getListings(@PathVariable long userId) {

        return listingService.findAllForUser(userId);
    }

    @PostMapping("/save")
    public Listing saveListing(@RequestBody ListingDTO listingDTO) {
        Long userId = 1L; // Should be implemented after JWT is implemented
        return listingService.createListing(listingDTO.getTitle(),listingDTO.getDescription(),userId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteListing(@PathVariable long id) {
        listingService.deleteListing(id);
    }

    @PutMapping("/update/{id}")
    public Listing updateListing(@PathVariable Long id, @RequestBody ListingDTO listing) {
        Listing updatedListing = listingService.updateListing(id,listing);
        return updatedListing;
    }

    @PutMapping("/deactivate/{id}")
    public void updateListing(@PathVariable Long id) {
        listingService.deactivateListing(id);
    }
}
