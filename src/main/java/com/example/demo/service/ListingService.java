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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public Listing getById(long id) {
        return listingRepository.getById(id);
    }

    public List<Listing> findAllNoFilter() {
        return listingRepository.findAll();
    }

    public List<Listing> findAll(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        if(search == null || search.isEmpty()) {
            return listingRepository.findAllActive(pageable).getContent();
        }
        String searchPattern = "%" + search + "%";
        Page<Listing> listings = listingRepository.findAllActiveWithSearch(searchPattern, pageable);
        return listings.getContent();
    }

    @Cacheable(value="listings", key="#userId + '-' + #page + '-' + #size")
    public List<Listing> findAllForUser(Long userId,int page, int size) {
        if (!userRepository.existsById(userId)) {
            throw new ExceptionNotFound("User not found");
        }
        User user = userRepository.findById(userId).get();
        System.out.println("Fetching listings...\n");
        Pageable pageable = PageRequest.of(page,size);
        Page<Listing> listings = listingRepository.findAllByAuthor(user,pageable);
        return listings.getContent();
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


    //Async method used for experimenting
    @Async
    public CompletableFuture<String> asyncFunction() throws InterruptedException {
        Thread.sleep(5000);
        return CompletableFuture.completedFuture("Async Function Response");
    }
}
