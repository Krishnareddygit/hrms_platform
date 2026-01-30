package com.example.security.config;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin@bounteous.com").isEmpty()) {
            User admin = User.builder()
                    .username("admin@bounteous.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .roles(Set.of("ADMIN"))
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created!");
        }
    }
}
