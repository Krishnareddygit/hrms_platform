package com.example.security.util;

import com.example.EmployeeManagement.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityUtil")
@RequiredArgsConstructor
public class SecurityUtil {

    private final EmployeeRepository employeeRepository;

    public boolean isSelf(Long employeeId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // email / username from JWT

        return employeeRepository.findById(employeeId)
                .map(emp -> emp.getCompanyEmail().equals(username))
                .orElse(false);
    }
}

