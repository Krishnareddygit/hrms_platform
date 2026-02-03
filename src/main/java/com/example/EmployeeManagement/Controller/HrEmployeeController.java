package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeCreateResponse;
import com.example.EmployeeManagement.DTO.EmployeeDTO;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Service.EmployeeService;
import com.example.security.dto.RegisterRequest;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.security.service.UserService;
import com.example.security.util.PasswordGenerator;

@RestController
@RequestMapping("/api/v1/hr/employees")
@RequiredArgsConstructor
public class HrEmployeeController {

    private final EmployeeService employeeService;
    private final UserRepository userRepository;

    private final  UserService userService;
    private final  PasswordGenerator passwordGenerator;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_HR_OPERATIONS')")
    public ResponseEntity<EmployeeCreateResponse> createEmployee(
            @RequestBody EmployeeDTO dto) {

        String hrUsername = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User hrUser = userRepository.findByUsername(hrUsername)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        //  Save Employee
        Employee employee = employeeService.toEntity(dto, hrUser.getId());
        EmployeeDTO savedEmployee = employeeService.addEmployee(employee);

        //  Create Employee Login
        String tempPassword = passwordGenerator.generateTempPassword();

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(savedEmployee.getCompanyEmail());
        registerRequest.setPassword(tempPassword);
        registerRequest.setEmployeeId(savedEmployee.getEmployeeId());

        userService.registerNewUser(registerRequest);

        // Return credentials
        return ResponseEntity.ok(
                new EmployeeCreateResponse(
                        savedEmployee.getEmployeeId(),
                        savedEmployee.getCompanyEmail(),
                        tempPassword
                )
        );
    }

}

