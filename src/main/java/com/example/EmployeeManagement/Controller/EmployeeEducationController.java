package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeEducationDTO;
import com.example.EmployeeManagement.DTO.EmployeeEducationUpdateDTO;
import com.example.EmployeeManagement.Model.EmployeeEducation;
import com.example.EmployeeManagement.Service.EmployeeEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/hrms/employees")
@RequiredArgsConstructor
public class EmployeeEducationController {

    private final EmployeeEducationService employeeEducationService;

    //  Get all education records
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/education")
    public ResponseEntity<List<EmployeeEducationDTO>> getAllEducations() {
        return ResponseEntity.ok(employeeEducationService.getAllEmployeesEducation());
    }

    // Get education by educationId
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/education/{educationId}")
    public ResponseEntity<EmployeeEducationDTO> getEducationById(@PathVariable Long educationId) {
        return ResponseEntity.ok(employeeEducationService.getEmployeeEducationById(educationId));
    }

    // Get all educations of an employee
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @GetMapping("/{employeeId}/education")
    public ResponseEntity<List<EmployeeEducationDTO>> getEducationByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeEducationService.getEmployeeEducationByEmployeeId(employeeId));
    }

//    add employee education by employee id
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PostMapping("/{employeeId}/add-education")
    public ResponseEntity<EmployeeEducationDTO> addEmployeeEducation(@PathVariable Long employeeId , @RequestBody EmployeeEducation employeeEducation){
        return ResponseEntity.ok(employeeEducationService.addEmployeeEducation(employeeId,employeeEducation));
    }

    //  Delete education by educationId
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/delete-education/{educationId}")
    public ResponseEntity<String> deleteByEducationId(@PathVariable Long educationId) {
        employeeEducationService.deleteEmployeeEducationById(educationId);
        return ResponseEntity.ok("Education record deleted successfully");
    }

    // Delete all education of an employee
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/education/{employeeId}")
    public ResponseEntity<String> deleteByEmployeeId(@PathVariable Long employeeId) {
        employeeEducationService.deleteEmployeeEducationByEmployeeId(employeeId);
        return ResponseEntity.ok("All education records deleted for employee " + employeeId);
    }

    //Partial update education (PATCH)
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PatchMapping("/{employeeId}/education/{educationId}")
    public ResponseEntity<EmployeeEducationDTO> updateEducation(
            @PathVariable Long employeeId,
            @PathVariable Long educationId,
            @RequestBody EmployeeEducationUpdateDTO dto) {

        EmployeeEducationDTO updated = employeeEducationService
                .updateEducation(educationId, employeeId, dto);

        return ResponseEntity.ok(updated);
    }
}

