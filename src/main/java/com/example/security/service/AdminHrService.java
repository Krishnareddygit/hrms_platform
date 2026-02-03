package com.example.security.service;

import com.example.security.constants.RoleConstants;
import com.example.security.dto.CreateHrRequest;
import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminHrService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createHr(CreateHrRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Mandatory EMPLOYEE role
        Role employeeRole = roleRepository.findByName(RoleConstants.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("ROLE_EMPLOYEE not found"));

        // HR role validation
        Role hrRole = roleRepository.findByName(request.getHrRole())
                .orElseThrow(() -> new RuntimeException("Invalid HR role"));

        if (!request.getHrRole().startsWith("ROLE_HR")) {
            throw new IllegalArgumentException("Invalid HR role assignment");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(employeeRole);
        roles.add(hrRole);

        User hrUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getTempPassword()))
                .enabled(true)
                .roles(roles)
                .build();

        return userRepository.save(hrUser);
    }
}

