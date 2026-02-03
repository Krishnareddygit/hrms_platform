package com.example.security.controller;

import com.example.EmployeeManagement.DTO.EmployeeDTO;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Service.EmployeeService;
import com.example.security.dto.AdminCreateEmployeeRequest;
import com.example.security.dto.RegisterRequest;
import com.example.security.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/employees")
@RequiredArgsConstructor
public class AdminEmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    @Transactional
//    public ResponseEntity<EmployeeDTO> createEmployeeWithTempLogin(
//            @Valid @RequestBody AdminCreateEmployeeRequest request) {
//
//        Employee employee = new Employee();
//        employee.setFirstName(request.getFirstName());
//        employee.setLastName(request.getLastName());
//        employee.setCompanyEmail(request.getCompanyEmail());
//        employee.setDateOfJoining(request.getDateOfJoining());
//        employee.setStatus(request.getStatus());
//        employee.setEmployeeType(request.getEmployeeType());
//        employee.setPhoneNumber(request.getPhoneNumber());
//        employee.setCurrentBand(request.getCurrentBand());
//        employee.setCurrentExperience(request.getCurrentExperience());
//        employee.setDesignation(request.getDesignation());
//        employee.setCtc(request.getCtc());
//
//        EmployeeDTO savedEmployee = employeeService.addEmployee(employee);
//
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setUsername(request.getCompanyEmail());
//        registerRequest.setPassword(request.getTempPassword());
//        registerRequest.setEmployeeId(savedEmployee.getEmployeeId());
//
//        userService.registerNewUser(registerRequest);
//
//        return ResponseEntity.ok(savedEmployee);
//    }
}

