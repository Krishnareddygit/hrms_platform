package com.example.EmployeeManagement.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeAddressDTO {

    private Long addressId;
    private Long employeeId;

    private String houseNumber;
    private String landmark;
    private String street;
    private String city;
    private String pincode;
    private String state;
    private String country;

    private Boolean isPermanent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
