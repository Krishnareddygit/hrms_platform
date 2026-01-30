package com.example.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateEmployeeRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String companyEmail;

    @NotBlank
    private String tempPassword;

    private LocalDate dateOfJoining;
    private String status;
    private String employeeType;
    private Long phoneNumber;
    private String currentBand;
    private double currentExperience;
    private String designation;
    private int ctc;
}

