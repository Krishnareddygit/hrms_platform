package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmploymentContractDTO;
import com.example.EmployeeManagement.Model.EmploymentContract;
import com.example.EmployeeManagement.Service.EmploymentContractService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmploymentContractController {

    private final EmploymentContractService contractService;

    // Get all contracts
    @GetMapping("/contracts")
    public ResponseEntity<List<EmploymentContractDTO>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    // Get contract by ID
    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<EmploymentContractDTO> getById(@PathVariable Long contractId) {
        return ResponseEntity.ok(contractService.getContractById(contractId));
    }

    // Get all contracts of employee
    @GetMapping("/{employeeId}/contracts")
    public ResponseEntity<List<EmploymentContractDTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(contractService.getContractsByEmployeeId(employeeId));
    }

    // Add contract
    @PostMapping("/{employeeId}/contracts")
    public ResponseEntity<EmploymentContractDTO> addContract(
            @PathVariable Long employeeId,
            @RequestBody EmploymentContract contract) {

        return ResponseEntity.ok(contractService.addContract(employeeId, contract));
    }

    // Update contract
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
    @DeleteMapping("/contracts/{contractId}")
    public ResponseEntity<Void> deleteByContractId(@PathVariable Long contractId) {
        contractService.deleteByContractId(contractId);
        return ResponseEntity.noContent().build();
    }

    // Delete all contracts of employee
    @DeleteMapping("/{employeeId}/contracts")
    public ResponseEntity<Void> deleteByEmployee(@PathVariable Long employeeId) {
        contractService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
