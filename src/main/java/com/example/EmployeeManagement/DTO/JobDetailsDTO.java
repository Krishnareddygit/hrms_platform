package com.example.EmployeeManagement.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JobDetailsDTO {

    private Long jobId;
    private Long employeeId;

    private String clientCompany;
    private String departmentName;
    private String baseLocation;
    private String clientLocation;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
