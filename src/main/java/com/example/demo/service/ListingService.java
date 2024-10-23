package com.example.demo.service;

import com.example.demo.models.Listing;
import com.example.demo.repository.ListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Listing> findAll() {
        return listingRepository.findAll();
    }
}
