package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeAddressDTO;
import com.example.EmployeeManagement.Model.EmployeeAddress;
import com.example.EmployeeManagement.Service.EmployeeAddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hrms/employees")
@AllArgsConstructor

public class EmployeeAddressController {

    private final EmployeeAddressService employeeAddressService;

    // Get all addresses
    @GetMapping("/addresses")
    public ResponseEntity<List<EmployeeAddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(employeeAddressService.getAllEmployeesAddress());
    }

    // Get address by addressId
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<EmployeeAddressDTO> getAddressById(@PathVariable Long addressId) {
        return ResponseEntity.ok(employeeAddressService.getEmployeeAddressById(addressId));
    }

    // Get all addresses of an employee
    @GetMapping("/{employeeId}/addresses")
    public ResponseEntity<List<EmployeeAddressDTO>> getAddressesByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeAddressService.getAddressByEmployeeId(employeeId));
    }

    //  Add address to employee
    @PostMapping("/{employeeId}/addresses")
    public ResponseEntity<EmployeeAddressDTO> addAddress(
            @PathVariable Long employeeId,
            @RequestBody EmployeeAddress employeeAddress) {

        return ResponseEntity.ok(
                employeeAddressService.addEmployeeAddress(employeeId, employeeAddress)
        );
    }

    //  Update address (partial update)
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
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteByAddressId(@PathVariable Long addressId) {
        employeeAddressService.deleteAddressById(addressId);
        return ResponseEntity.ok("Address deleted successfully");
    }

    // Delete all addresses of an employeeId
    @DeleteMapping("/{employeeId}/addresses")
    public ResponseEntity<String> deleteByEmployeeId(@PathVariable Long employeeId) {
        employeeAddressService.deleteAddressByEmployeeId(employeeId);
        return ResponseEntity.ok("Address deleted successfully of employee "+employeeId);
    }
}
