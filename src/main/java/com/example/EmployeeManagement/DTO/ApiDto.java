package com.example.EmployeeManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;

}
