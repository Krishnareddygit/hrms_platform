package com.example.EmployeeManagement.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExperienceDTO {

    private Long experienceId;
    private Long employeeId;

    private String company;
    private String designation;

    private LocalDate fromDate;
    private LocalDate toDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
