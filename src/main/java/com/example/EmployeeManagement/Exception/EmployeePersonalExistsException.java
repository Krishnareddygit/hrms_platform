package com.example.EmployeeManagement.Exception;

public class EmployeePersonalExistsException extends RuntimeException {
    public EmployeePersonalExistsException(Long id) {
        super("Employee personal details already exists for employee id: "+id);
    }
}
