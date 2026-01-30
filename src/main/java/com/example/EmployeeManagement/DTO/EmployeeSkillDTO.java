package com.example.EmployeeManagement.DTO;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmployeeSkillDTO {

    private Long employeeSkillId;
    private Long employeeId;

    private String skillName;
    private String skillType;
    private String proficiencyLevel;

    private Integer yearsOfExperience;
    private Integer lastUsedYear;
    private Boolean isPrimary;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

