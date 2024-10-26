package com.example.demo.repository;

import com.example.demo.models.Listing;
import com.example.demo.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    @Query("SELECT l FROM Listing l WHERE l.author=:user AND l.isDeactivated=false")
    public Page<Listing> findAllByAuthor(User user, Pageable pageable);

    @Query("SELECT l FROM Listing l WHERE l.author=:user AND l.isDeactivated=true")
    public List<Listing> findAllDeactivatedByAuthor(User user);

    @Query ("SELECT l.isDeactivated FROM Listing l WHERE l.id=:id")
    public boolean isDeactivated (Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Listing l SET l.isDeactivated = true WHERE l.id=:id")
    public void deactivateListing(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Listing l SET l.isDeactivated = false WHERE l.id=:id")
    public void activateListing(Long id);
}
