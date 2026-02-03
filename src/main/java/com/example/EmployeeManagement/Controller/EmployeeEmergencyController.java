package com.example.EmployeeManagement.Controller;


import com.example.EmployeeManagement.DTO.EmployeeEmergencyDTO;
import com.example.EmployeeManagement.Model.EmployeeEmergency;
import com.example.EmployeeManagement.Service.EmployeeEmergencyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeEmergencyController {

    private final EmployeeEmergencyService employeeEmergencyService;

    // Get all emergency contacts
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/emergencies")
    public ResponseEntity<List<EmployeeEmergencyDTO>> getAllEmergencies() {
        return ResponseEntity.ok(employeeEmergencyService.getAllEmergencies());
    }

    // Get emergency by id
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/emergencies/{emergencyId}")
    public ResponseEntity<EmployeeEmergencyDTO> getById(@PathVariable Long emergencyId) {
        return ResponseEntity.ok(employeeEmergencyService.getEmergencyById(emergencyId));
    }

    // Get all emergencies of an employee
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @GetMapping("/{employeeId}/emergencies")
    public ResponseEntity<List<EmployeeEmergencyDTO>> getByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeEmergencyService.getEmergenciesByEmployeeId(employeeId));
    }

    // Add emergency contact
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PostMapping("/{employeeId}/emergencies")
    public ResponseEntity<EmployeeEmergencyDTO> addEmergency(
            @PathVariable Long employeeId,
            @RequestBody EmployeeEmergency emergency) {

        return ResponseEntity.ok(employeeEmergencyService.addEmergency(employeeId, emergency));
    }

    // Update emergency (partial update)
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PutMapping("/{employeeId}/emergencies/{emergencyId}")
    public ResponseEntity<EmployeeEmergencyDTO> updateEmergency(
            @PathVariable Long employeeId,
            @PathVariable Long emergencyId,
            @RequestBody EmployeeEmergencyDTO dto) {

        return ResponseEntity.ok(
                employeeEmergencyService.updateEmergency(emergencyId, employeeId, dto)
        );
    }

    // Delete one emergency
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/emergencies/{emergencyId}")
    public ResponseEntity<Void> deleteByEmergencyId(@PathVariable Long emergencyId) {
        employeeEmergencyService.deleteByEmergencyId(emergencyId);
        return ResponseEntity.noContent().build();
    }

    // Delete all emergencies of employee
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/{employeeId}/emergencies")
    public ResponseEntity<Void> deleteByEmployeeId(@PathVariable Long employeeId) {
        employeeEmergencyService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}

