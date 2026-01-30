package com.example.EmployeeManagement.DTO;

import lombok.Data;

@Data
public class EmployeeEducationUpdateDTO {
    private String year;
    private String grade;
    private String degree;
    private String institution;
    private Boolean isHighest;
}
