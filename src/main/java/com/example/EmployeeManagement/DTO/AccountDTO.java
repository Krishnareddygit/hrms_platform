package com.example.EmployeeManagement.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccountDTO {

    private Long accountId;
    private Long employeeId;

    private String accountNumber;
    private String bankName;
    private String branchName;
    private String ifscCode;
    private String accountType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
