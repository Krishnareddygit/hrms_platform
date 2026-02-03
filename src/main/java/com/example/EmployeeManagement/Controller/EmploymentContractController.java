package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmploymentContractDTO;
import com.example.EmployeeManagement.Model.EmploymentContract;
import com.example.EmployeeManagement.Service.EmploymentContractService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmploymentContractController {

    private final EmploymentContractService contractService;

    // Get all contracts
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','HR_PAYROLL','ADMIN')")
    @GetMapping("/contracts")
    public ResponseEntity<List<EmploymentContractDTO>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    // Get contract by ID
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','HR_PAYROLL','ADMIN')")
    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<EmploymentContractDTO> getById(@PathVariable Long contractId) {
        return ResponseEntity.ok(contractService.getContractById(contractId));
    }

    // Get all contracts of employee
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','HR_PAYROLL','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @GetMapping("/{employeeId}/contracts")
    public ResponseEntity<List<EmploymentContractDTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(contractService.getContractsByEmployeeId(employeeId));
    }

    // Add contract
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PostMapping("/{employeeId}/contracts")
    public ResponseEntity<EmploymentContractDTO> addContract(
            @PathVariable Long employeeId,
            @RequestBody EmploymentContract contract) {

        return ResponseEntity.ok(contractService.addContract(employeeId, contract));
    }

    // Update contract
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PutMapping("/{employeeId}/contracts/{contractId}")
    public ResponseEntity<EmploymentContractDTO> updateContract(
            @PathVariable Long employeeId,
            @PathVariable Long contractId,
            @RequestBody EmploymentContractDTO dto) {

        return ResponseEntity.ok(
                contractService.updateContract(contractId, employeeId, dto)
        );
    }

    // Delete one contract
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','HR_PAYROLL','ADMIN')")
    @DeleteMapping("/contracts/{contractId}")
    public ResponseEntity<Void> deleteByContractId(@PathVariable Long contractId) {
        contractService.deleteByContractId(contractId);
        return ResponseEntity.noContent().build();
    }

    // Delete all contracts of employee
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','HR_PAYROLL','ADMIN')")
    @DeleteMapping("/{employeeId}/contracts")
    public ResponseEntity<Void> deleteByEmployee(@PathVariable Long employeeId) {
        contractService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
