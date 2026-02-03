package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeManagerHistoryDTO;
import com.example.EmployeeManagement.Model.EmployeeManagerHistory;
import com.example.EmployeeManagement.Service.EmployeeManagerHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeManagerHistoryController {

    private final EmployeeManagerHistoryService historyService;

    // Get all manager history
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/manager-history")
    public ResponseEntity<List<EmployeeManagerHistoryDTO>> getAll() {
        return ResponseEntity.ok(historyService.getAll());
    }

    // Get history by ID
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/manager-history/{historyId}")
    public ResponseEntity<EmployeeManagerHistoryDTO> getById(@PathVariable Long historyId) {
        return ResponseEntity.ok(historyService.getById(historyId));
    }

    // Get manager history of employee
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','HR_BP','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @GetMapping("/{employeeId}/manager-history")
    public ResponseEntity<List<EmployeeManagerHistoryDTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(historyService.getByEmployeeId(employeeId));
    }

    // Get employees under a manager
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','HR_BP','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#managerId))
    """)
    @GetMapping("/manager/{managerId}/employees")
    public ResponseEntity<List<EmployeeManagerHistoryDTO>> getByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(historyService.getByManagerId(managerId));
    }

    // Add manager history
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PostMapping("/{employeeId}/manager-history")
    public ResponseEntity<EmployeeManagerHistoryDTO> addHistory(
            @PathVariable Long employeeId,
            @RequestBody EmployeeManagerHistory history) {

        return ResponseEntity.ok(historyService.addHistory(employeeId, history));
    }

    // Update manager history
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PutMapping("/{employeeId}/manager-history/{historyId}")
    public ResponseEntity<EmployeeManagerHistoryDTO> updateHistory(
            @PathVariable Long employeeId,
            @PathVariable Long historyId,
            @RequestBody EmployeeManagerHistoryDTO dto) {

        return ResponseEntity.ok(
                historyService.updateHistory(historyId, employeeId, dto)
        );
    }

    // Delete one history
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/manager-history/{historyId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long historyId) {
        historyService.deleteById(historyId);
        return ResponseEntity.noContent().build();
    }

    // Delete all history of employee
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/{employeeId}/manager-history")
    public ResponseEntity<Void> deleteByEmployee(@PathVariable Long employeeId) {
        historyService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
