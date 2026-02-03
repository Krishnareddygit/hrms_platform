package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeBandDTO;
import com.example.EmployeeManagement.Model.EmployeeBand;
import com.example.EmployeeManagement.Service.EmployeeBandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeBandController {

    private final EmployeeBandService bandService;

    // Get all band history
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','HR_BP','ADMIN')")
    @GetMapping("/bands")
    public ResponseEntity<List<EmployeeBandDTO>> getAllBands() {
        return ResponseEntity.ok(bandService.getAllBands());
    }

    // Get band history by ID
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','HR_BP','ADMIN')")
    @GetMapping("/bands/{historyId}")
    public ResponseEntity<EmployeeBandDTO> getById(@PathVariable Long historyId) {
        return ResponseEntity.ok(bandService.getById(historyId));
    }

    // Get band history of employee
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','HR_BP','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @GetMapping("/{employeeId}/bands")
    public ResponseEntity<List<EmployeeBandDTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(bandService.getByEmployeeId(employeeId));
    }

    // Add band history
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PostMapping("/{employeeId}/bands")
    public ResponseEntity<EmployeeBandDTO> addBand(
            @PathVariable Long employeeId,
            @RequestBody EmployeeBand band) {

        return ResponseEntity.ok(bandService.addBand(employeeId, band));
    }

    // Update band history
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PutMapping("/{employeeId}/bands/{historyId}")
    public ResponseEntity<EmployeeBandDTO> updateBand(
            @PathVariable Long employeeId,
            @PathVariable Long historyId,
            @RequestBody EmployeeBandDTO dto) {

        return ResponseEntity.ok(
                bandService.updateBand(historyId, employeeId, dto)
        );
    }

    // Delete one band history
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/bands/{historyId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long historyId) {
        bandService.deleteById(historyId);
        return ResponseEntity.noContent().build();
    }

    // Delete all band history of employee
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/{employeeId}/bands")
    public ResponseEntity<Void> deleteByEmployee(@PathVariable Long employeeId) {
        bandService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
