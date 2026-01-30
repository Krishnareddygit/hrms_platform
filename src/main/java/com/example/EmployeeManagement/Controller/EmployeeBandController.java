package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeBandDTO;
import com.example.EmployeeManagement.Model.EmployeeBand;
import com.example.EmployeeManagement.Service.EmployeeBandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeBandController {

    private final EmployeeBandService bandService;

    // Get all band history
    @GetMapping("/bands")
    public ResponseEntity<List<EmployeeBandDTO>> getAllBands() {
        return ResponseEntity.ok(bandService.getAllBands());
    }

    // Get band history by ID
    @GetMapping("/bands/{historyId}")
    public ResponseEntity<EmployeeBandDTO> getById(@PathVariable Long historyId) {
        return ResponseEntity.ok(bandService.getById(historyId));
    }

    // Get band history of employee
    @GetMapping("/{employeeId}/bands")
    public ResponseEntity<List<EmployeeBandDTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(bandService.getByEmployeeId(employeeId));
    }

    // Add band history
    @PostMapping("/{employeeId}/bands")
    public ResponseEntity<EmployeeBandDTO> addBand(
            @PathVariable Long employeeId,
            @RequestBody EmployeeBand band) {

        return ResponseEntity.ok(bandService.addBand(employeeId, band));
    }

    // Update band history
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
    @DeleteMapping("/bands/{historyId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long historyId) {
        bandService.deleteById(historyId);
        return ResponseEntity.noContent().build();
    }

    // Delete all band history of employee
    @DeleteMapping("/{employeeId}/bands")
    public ResponseEntity<Void> deleteByEmployee(@PathVariable Long employeeId) {
        bandService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
