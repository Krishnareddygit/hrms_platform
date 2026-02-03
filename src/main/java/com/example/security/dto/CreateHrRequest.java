package com.example.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateHrRequest {
    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String tempPassword;

    @NotBlank
    private String hrRole;
    // ROLE_HR_OPERATIONS, ROLE_HR_PAYROLL, ROLE_HR_BP, ROLE_TALENT_ACQUISITION
}
