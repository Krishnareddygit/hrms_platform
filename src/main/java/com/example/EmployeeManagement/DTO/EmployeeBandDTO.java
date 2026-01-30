package com.example.EmployeeManagement.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeBandDTO {

    private Long historyId;
    private Long employeeId;

    private String bandLevel;

    private LocalDate fromDate;
    private LocalDate toDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
