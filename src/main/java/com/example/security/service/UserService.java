package com.example.security.service;

import com.example.security.constants.RoleConstants;
import com.example.security.dto.ChangePasswordRequest;
import com.example.security.dto.RegisterRequest;
import com.example.security.dto.ResetPasswordRequest;
import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    /**
     * Register a new user.
     * Default role: ROLE_EMPLOYEE
     */
    @Transactional
    public User registerNewUser(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }

        Role employeeRole = roleRepository
                .findByName(RoleConstants.ROLE_EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("ROLE_EMPLOYEE not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(employeeRole);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .employeeId(request.getEmployeeId())
                .enabled(true)
                .roles(roles)
                .mustChangePassword(true)   // 🔐 FORCE FIRST LOGIN CHANGE
                .build();

        return userRepository.save(user);
    }


    //change password for logged-in users
    @Transactional
    public void changePassword(String username, ChangePasswordRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setMustChangePassword(false); // ✅ unlock account
        userRepository.save(user);
    }


    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        user.setMustChangePassword(false);

        userRepository.save(user);
    }

    @Transactional
    public void generateResetToken(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        String resetLink =
                "http://localhost:8080/reset-password?token=" + token;

        emailService.sendPasswordResetEmail(
                user.getUsername(),
                resetLink
        );
    }

}
