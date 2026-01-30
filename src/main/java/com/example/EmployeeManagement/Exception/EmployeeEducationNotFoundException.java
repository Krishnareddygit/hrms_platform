package com.example.EmployeeManagement.Exception;

public class EmployeeEducationNotFoundException extends RuntimeException {
    public EmployeeEducationNotFoundException(Long id) {
        super("Employee education not found for this education id: "+id);
    }
}
