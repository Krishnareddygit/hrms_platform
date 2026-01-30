package com.example.EmployeeManagement.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmploymentContractDTO {

    private Long contractId;
    private Long employeeId;

    private LocalDate contractStart;
    private LocalDate contractEnd;
    private String contractType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
