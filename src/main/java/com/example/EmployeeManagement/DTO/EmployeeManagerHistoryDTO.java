package com.example.EmployeeManagement.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeManagerHistoryDTO {

    private Long historyId;
    private Long employeeId;

    private Long managerId;

    private LocalDate fromDate;
    private LocalDate toDate;

    private String reason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
