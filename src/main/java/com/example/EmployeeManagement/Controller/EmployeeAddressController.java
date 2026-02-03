package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeAddressDTO;
import com.example.EmployeeManagement.Model.EmployeeAddress;
import com.example.EmployeeManagement.Service.EmployeeAddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hrms/employees")
@AllArgsConstructor

public class EmployeeAddressController {

    private final EmployeeAddressService employeeAddressService;

    // Get all addresses
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/addresses")
    public ResponseEntity<List<EmployeeAddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(employeeAddressService.getAllEmployeesAddress());
    }

    // Get address by addressId
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<EmployeeAddressDTO> getAddressById(@PathVariable Long addressId) {
        return ResponseEntity.ok(employeeAddressService.getEmployeeAddressById(addressId));
    }

    // Get all addresses of an employee
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @GetMapping("/{employeeId}/addresses")
    public ResponseEntity<List<EmployeeAddressDTO>> getAddressesByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeAddressService.getAddressByEmployeeId(employeeId));
    }

    //  Add address to employee
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PostMapping("/{employeeId}/addresses")
    public ResponseEntity<EmployeeAddressDTO> addAddress(
            @PathVariable Long employeeId,
            @RequestBody EmployeeAddress employeeAddress) {

        return ResponseEntity.ok(
                employeeAddressService.addEmployeeAddress(employeeId, employeeAddress)
        );
    }

    //  Update address (partial update)
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isself(#employeeId))
    """)
    @PatchMapping("/{employeeId}/addresses/{addressId}")
    public ResponseEntity<EmployeeAddressDTO> updateAddress(
            @PathVariable Long employeeId,
            @PathVariable Long addressId,
            @RequestBody EmployeeAddressDTO dto) {

        return ResponseEntity.ok(
                employeeAddressService.updateEmployeeAddress(addressId, employeeId, dto)
        );
    }

    // Delete address by addressId
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteByAddressId(@PathVariable Long addressId) {
        employeeAddressService.deleteAddressById(addressId);
        return ResponseEntity.ok("Address deleted successfully");
    }

    // Delete all addresses of an employeeId
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/{employeeId}/addresses")
    public ResponseEntity<String> deleteByEmployeeId(@PathVariable Long employeeId) {
        employeeAddressService.deleteAddressByEmployeeId(employeeId);
        return ResponseEntity.ok("Address deleted successfully of employee "+employeeId);
    }
}
