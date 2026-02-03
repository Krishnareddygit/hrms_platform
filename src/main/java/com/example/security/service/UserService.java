package com.example.security.service;

import com.example.security.constants.RoleConstants;
import com.example.security.dto.ChangePasswordRequest;
import com.example.security.dto.RegisterRequest;
import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new user.
     * Default role: ROLE_EMPLOYEE
     */
    @Transactional
    public User registerNewUser(RegisterRequest request) {

        //  Check username uniqueness
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }

        //  Fetch mandatory EMPLOYEE role
        Role employeeRole = roleRepository
                .findByName(RoleConstants.ROLE_EMPLOYEE)
                .orElseThrow(() ->
                        new RuntimeException("ROLE_EMPLOYEE not found in database"));

        //  Assign default role
        Set<Role> roles = new HashSet<>();
        roles.add(employeeRole);

        // Create user
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .employeeId(request.getEmployeeId())
                .enabled(true)
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    //change password for logged-in users
    @Transactional
    public void changePassword(String username, ChangePasswordRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found: " + username));

        // Validate old password
        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword()
        )) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
