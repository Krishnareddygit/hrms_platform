package com.example.security.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PasswordGenerator {

    public String generateTempPassword() {
        return "Emp@" + UUID.randomUUID().toString().substring(0, 8);
    }
}

