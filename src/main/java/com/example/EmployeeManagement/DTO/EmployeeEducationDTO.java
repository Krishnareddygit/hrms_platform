package com.example.EmployeeManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEducationDTO {

    private Long employeeEducationId;
    private Long employeeId;
    private String year;
    private String grade;
    private String degree;
    private String institution;

    private Boolean isHighest;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
