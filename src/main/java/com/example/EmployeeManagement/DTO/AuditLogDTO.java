package com.example.EmployeeManagement.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditLogDTO {

    private Long auditId;

    private String tableName;
    private String fieldName;

    private String oldValue;
    private String newValue;

    private String actionType;

    private Long changedBy;
    private LocalDateTime changedAt;

    private Boolean approvalRequired;
    private Long approvedBy;
    private LocalDateTime approvedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
