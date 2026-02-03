package com.example.EmployeeManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeCreateResponse {

    private Long employeeId;
    private String username;
    private String tempPassword;
}

