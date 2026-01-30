package com.example.EmployeeManagement.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeEmergencyDTO {
    private Long emergencyId;

    private Long employeeId;

    private String contactName;
    private String relation;
    private String phoneNumber;

    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
