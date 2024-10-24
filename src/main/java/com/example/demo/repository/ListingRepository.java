package com.example.demo.repository;

import com.example.demo.models.Listing;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    public List<Listing> findAllByAuthor(User user);
}
