package com.example.demo.repository;

import com.example.demo.models.Listing;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, ListingRepository listingRepository) {
        return args -> {
            User user1 = new User(null, "John", "Doe", "john.doe@test.com", passwordEncoder.encode("123"), Role.ROLE_USER);
            User user2 = new User(null, "Nikola", "Jokic", "string", passwordEncoder.encode("string"), Role.ROLE_ADMIN);

            userRepository.save(user1);
            userRepository.save(user2);

            Listing listing1 = new Listing(null, "Laptop for Sale", "Selling a laptop.", user1,false);
            Listing listing2 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing3 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing4 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing5 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing6 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing7 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing8 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing9 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing10 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing11 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing12 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing13 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing14 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);
            Listing listing15 = new Listing(null, "Bicycle for Sale", "Selling a bicycle.", user2,false);

            listingRepository.save(listing1);
            listingRepository.save(listing2);
            listingRepository.save(listing3);
            listingRepository.save(listing4);
            listingRepository.save(listing5);
            listingRepository.save(listing6);
            listingRepository.save(listing7);
            listingRepository.save(listing8);
            listingRepository.save(listing9);
            listingRepository.save(listing10);
            listingRepository.save(listing11);
            listingRepository.save(listing12);
            listingRepository.save(listing13);
            listingRepository.save(listing14);
            listingRepository.save(listing15);
        };
    }
}
