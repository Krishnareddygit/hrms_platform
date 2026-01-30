package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.AuditLogDTO;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Model.AuditLog;
import com.example.EmployeeManagement.Repository.AuditLogRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    // Get all audit logs
    public List<AuditLogDTO> getAllLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get by auditId
    public AuditLogDTO getById(Long id) {
        AuditLog log = auditLogRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Audit log not found with id: " + id));
        return mapToDto(log);
    }

    // Get by table name
    public List<AuditLogDTO> getByTableName(String tableName) {
        return auditLogRepository.findByTableName(tableName)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get by employee who changed
    public List<AuditLogDTO> getByChangedBy(Long employeeId) {
        return auditLogRepository.findByChangedBy(employeeId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get logs that require approval
    public List<AuditLogDTO> getPendingApprovals() {
        return auditLogRepository.findPendingApprovals()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Add audit log
    public AuditLogDTO addLog(AuditLog log) {

        log.setCreatedAt(LocalDateTime.now());
        log.setUpdatedAt(LocalDateTime.now());
        log.setChangedAt(LocalDateTime.now());

        return mapToDto(auditLogRepository.save(log));
    }

    // Approve audit
    @Transactional
    public AuditLogDTO approveAudit(Long auditId, Long approverId) {

        AuditLog log = auditLogRepository.findById(auditId)
                .orElseThrow(() -> new DataNotFoundException("Audit log not found with id: " + auditId));

        log.setApprovedBy(approverId);
        log.setApprovedAt(LocalDateTime.now());
        log.setApprovalRequired(false);
        log.setUpdatedAt(LocalDateTime.now());

        return mapToDto(log);
    }

    // Mapper
    private AuditLogDTO mapToDto(AuditLog log) {

        AuditLogDTO dto = new AuditLogDTO();
        dto.setAuditId(log.getAuditId());
        dto.setTableName(log.getTableName());
        dto.setFieldName(log.getFieldName());
        dto.setOldValue(log.getOldValue());
        dto.setNewValue(log.getNewValue());
        dto.setActionType(log.getActionType());
        dto.setChangedBy(log.getChangedBy());
        dto.setChangedAt(log.getChangedAt());
        dto.setApprovalRequired(log.getApprovalRequired());
        dto.setApprovedBy(log.getApprovedBy());
        dto.setApprovedAt(log.getApprovedAt());
        dto.setCreatedAt(log.getCreatedAt());
        dto.setUpdatedAt(log.getUpdatedAt());

        return dto;
    }
}
