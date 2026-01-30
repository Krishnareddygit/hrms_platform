package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeEducationDTO;
import com.example.EmployeeManagement.DTO.EmployeeEducationUpdateDTO;
import com.example.EmployeeManagement.Model.EmployeeEducation;
import com.example.EmployeeManagement.Service.EmployeeEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/hrms/employees")
@RequiredArgsConstructor
public class EmployeeEducationController {

    private final EmployeeEducationService employeeEducationService;

    //  Get all education records
    @GetMapping("/education")
    public ResponseEntity<List<EmployeeEducationDTO>> getAllEducations() {
        return ResponseEntity.ok(employeeEducationService.getAllEmployeesEducation());
    }

    // Get education by educationId
    @GetMapping("/education/{educationId}")
    public ResponseEntity<EmployeeEducationDTO> getEducationById(@PathVariable Long educationId) {
        return ResponseEntity.ok(employeeEducationService.getEmployeeEducationById(educationId));
    }

    // Get all educations of an employee
    @GetMapping("/{employeeId}/education")
    public ResponseEntity<List<EmployeeEducationDTO>> getEducationByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeEducationService.getEmployeeEducationByEmployeeId(employeeId));
    }

//    add employee education by employee id
    @PostMapping("/{employeeId}/add-education")
    public ResponseEntity<EmployeeEducationDTO> addEmployeeEducation(@PathVariable Long employeeId , @RequestBody EmployeeEducation employeeEducation){
        return ResponseEntity.ok(employeeEducationService.addEmployeeEducation(employeeId,employeeEducation));
    }

    //  Delete education by educationId
    @DeleteMapping("/delete-education/{educationId}")
    public ResponseEntity<String> deleteByEducationId(@PathVariable Long educationId) {
        employeeEducationService.deleteEmployeeEducationById(educationId);
        return ResponseEntity.ok("Education record deleted successfully");
    }

    // Delete all education of an employee
    @DeleteMapping("/education/{employeeId}")
    public ResponseEntity<String> deleteByEmployeeId(@PathVariable Long employeeId) {
        employeeEducationService.deleteEmployeeEducationByEmployeeId(employeeId);
        return ResponseEntity.ok("All education records deleted for employee " + employeeId);
    }

    //Partial update education (PATCH)
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

