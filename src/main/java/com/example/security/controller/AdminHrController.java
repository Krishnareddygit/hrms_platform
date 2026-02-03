package com.example.security.controller;

import com.example.security.dto.CreateHrRequest;
import com.example.security.service.AdminHrService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/hr")
@RequiredArgsConstructor
public class AdminHrController {

    private final AdminHrService adminHrService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createHr(@Valid @RequestBody CreateHrRequest request) {

        adminHrService.createHr(request);
        return ResponseEntity.ok("HR created successfully");
    }
}

