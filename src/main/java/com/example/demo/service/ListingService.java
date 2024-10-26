package com.example.demo.service;

import com.example.demo.DTOs.ListingDTO;
import com.example.demo.exception.ExceptionForbidden;
import com.example.demo.exception.ExceptionNotFound;
import com.example.demo.models.Listing;
import com.example.demo.models.User;
import com.example.demo.repository.ListingRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }
    public List<Listing> findAll() {
        return listingRepository.findAll();
    }
    @Cacheable(value="listings", key="#userId")
    public List<Listing> findAllForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ExceptionNotFound("User not found");
        }
        User user = userRepository.findById(userId).get();
        System.out.println("Fetching listings...\n");
        return listingRepository.findAllByAuthor(user);
    }

    public List<Listing> findAllDeactivatedForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ExceptionNotFound("User not found");
        }
        User user = userRepository.findById(userId).get();
        return listingRepository.findAllDeactivatedByAuthor(user);
    }
    @CacheEvict(value = "listings", allEntries = true)
    public Listing createListing(String title, String description,Long userId) {
        if (!userRepository.existsById(userId)){
            throw new ExceptionNotFound("Userid not found");
        }
        User author = userRepository.findById(userId).get();
        Listing listing = new Listing();
        listing.setTitle(title);
        listing.setDescription(description);
        listing.setAuthor(author);
        return listingRepository.save(listing);
    }

    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }
    @CacheEvict(value = "listings", allEntries = true)
    public void deleteListing(Long userId, Long listingId) {
        if (!listingRepository.existsById(listingId)) {
            throw new ExceptionNotFound("Listing not found");
        }
        Listing currentListing = listingRepository.findById(listingId).get();
        if(currentListing.getAuthor().getId()!=userId){
            throw new ExceptionForbidden("This listing does not belong to this user");
        }
        listingRepository.deleteById(listingId);
    }
    @CacheEvict(value = "listings", allEntries = true)
    public Listing updateListing (Long userId, Long listingId, ListingDTO listing){
        if (!listingRepository.existsById(listingId) || listingRepository.isDeactivated(listingId)) {
            throw new ExceptionNotFound("Listing not found");
        }
        Listing currentListing = listingRepository.findById(listingId).get();
        if(currentListing.getAuthor().getId()!=userId){
            throw new ExceptionForbidden("This listing does not belong to this user");
        }

        if (listing.getTitle() != null) {
            currentListing.setTitle(listing.getTitle());
        }
        if (listing.getDescription() != null) {
            currentListing.setDescription(listing.getDescription());
        }
        return listingRepository.save(currentListing);
    }
    @CacheEvict(value = "listings", allEntries = true)
    public void deactivateListing(Long userId, Long listingId) {
        if (!listingRepository.existsById(listingId) || listingRepository.isDeactivated(listingId)) {
            throw new ExceptionNotFound("Listing not found");
        }
        Listing currentListing = listingRepository.findById(listingId).get();
        if(currentListing.getAuthor().getId()!=userId){
            throw new ExceptionForbidden("This listing does not belong to this user");
        }

        listingRepository.deactivateListing(listingId);
    }
    @CacheEvict(value = "listings", allEntries = true)
    public void activateListing(Long userId, Long listingId) {
        if (!listingRepository.existsById(listingId) || !listingRepository.isDeactivated(listingId)) {
            throw new ExceptionNotFound("Listing not found");
        }
        Listing currentListing = listingRepository.findById(listingId).get();
        if(currentListing.getAuthor().getId()!=userId){
            throw new ExceptionForbidden("This listing does not belong to this user");
        }

        listingRepository.activateListing(listingId);
    }
}
