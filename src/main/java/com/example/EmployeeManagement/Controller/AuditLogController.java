package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.AuditLogDTO;
import com.example.EmployeeManagement.Model.AuditLog;
import com.example.EmployeeManagement.Service.AuditLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
@AllArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    // Get all audit logs
    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAll() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }

    // Get by audit id
    @GetMapping("/{auditId}")
    public ResponseEntity<AuditLogDTO> getById(@PathVariable Long auditId) {
        return ResponseEntity.ok(auditLogService.getById(auditId));
    }

    // Get logs by table name
    @GetMapping("/table/{tableName}")
    public ResponseEntity<List<AuditLogDTO>> getByTable(@PathVariable String tableName) {
        return ResponseEntity.ok(auditLogService.getByTableName(tableName));
    }

    // Get logs by employee who changed
    @GetMapping("/changed-by/{employeeId}")
    public ResponseEntity<List<AuditLogDTO>> getByChangedBy(@PathVariable Long employeeId) {
        return ResponseEntity.ok(auditLogService.getByChangedBy(employeeId));
    }

    // Get pending approvals
    @GetMapping("/pending")
    public ResponseEntity<List<AuditLogDTO>> getPending() {
        return ResponseEntity.ok(auditLogService.getPendingApprovals());
    }

    // Create audit log
    @PostMapping
    public ResponseEntity<AuditLogDTO> add(@RequestBody AuditLog log) {
        return ResponseEntity.ok(auditLogService.addLog(log));
    }

    // Approve audit
    @PutMapping("/{auditId}/approve/{approverId}")
    public ResponseEntity<AuditLogDTO> approve(
            @PathVariable Long auditId,
            @PathVariable Long approverId) {

        return ResponseEntity.ok(
                auditLogService.approveAudit(auditId, approverId)
        );
    }
}
