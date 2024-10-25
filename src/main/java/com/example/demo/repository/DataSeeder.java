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

            listingRepository.save(listing1);
            listingRepository.save(listing2);
        };
    }
}
