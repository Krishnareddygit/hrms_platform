package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    @Query("""
        SELECT a
        FROM AuditLog a
        WHERE a.tableName = :tableName
    """)
    List<AuditLog> findByTableName(@Param("tableName") String tableName);

    @Query("""
        SELECT a
        FROM AuditLog a
        WHERE a.changedBy = :changedBy
    """)
    List<AuditLog> findByChangedBy(@Param("changedBy") Long changedBy);

    @Query("""
        SELECT a
        FROM AuditLog a
        WHERE a.approvalRequired = true
    """)
    List<AuditLog> findPendingApprovals();
}
