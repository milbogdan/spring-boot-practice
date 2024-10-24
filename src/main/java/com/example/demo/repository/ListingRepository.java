package com.example.demo.repository;

import com.example.demo.models.Listing;
import com.example.demo.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
    public List<Listing> findAllByAuthor(User user);

    @Transactional
    @Modifying
    @Query("UPDATE Listing l SET l.isDeactivated = true WHERE l.id=:id")
    public void deactivateListing(Long id);
}
