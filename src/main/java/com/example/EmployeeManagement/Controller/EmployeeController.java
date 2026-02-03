package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeDTO;
import com.example.EmployeeManagement.Service.EmployeeService;
import com.example.EmployeeManagement.Model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hrms")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping("/")
    public String message(){
        return "Hello";
    }

    @PreAuthorize("hasAnyRole('HR_OPERATIONS','HR_BP','ADMIN')")
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        List<EmployeeDTO> employees = employeeService.getAllEmployee();
        return ResponseEntity.ok(employees);
    }

    // GET /api/v1/hrms/employees/{id}
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','HR_BP','ADMIN')")
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeCoreById(@PathVariable("id") Long id){
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // GET /api/v1/hrms/employees/{id}/profile
    @PreAuthorize("""
    hasAnyRole('HR_OPERATIONS','HR_BP','ADMIN')
    or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#id))
""")
    @GetMapping("/employees/{id}/profile")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long id){
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody Employee employee){
        EmployeeDTO savedEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    // PUT /api/v1/hrms/employees/{id}
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") Long id,
                                                      @RequestBody Employee employee){
        EmployeeDTO updated = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id){
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }


    @PreAuthorize("hasAnyRole('HR_OPERATIONS','HR_BP','EMPLOYEE')")
    @GetMapping("/manager/{managerId}/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesUnderManager(
            @PathVariable Long managerId) {

        return ResponseEntity.ok(
                employeeService.getEmployeesUnderManager(managerId)
        );
    }


    @GetMapping("/debug/auth")
    public Object debugAuth(Authentication authentication) {
        return authentication.getAuthorities();
    }

}
