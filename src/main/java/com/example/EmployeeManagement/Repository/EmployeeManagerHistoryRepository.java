package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.EmployeeManagerHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeManagerHistoryRepository extends JpaRepository<EmployeeManagerHistory, Long> {

    @Query("""
        SELECT h FROM EmployeeManagerHistory h
        WHERE h.employee.employeeId = :employeeId
        ORDER BY h.fromDate DESC
    """)
    List<EmployeeManagerHistory> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("""
        SELECT h FROM EmployeeManagerHistory h
        WHERE h.managerId = :managerId
    """)
    List<EmployeeManagerHistory> findByManagerId(@Param("managerId") Long managerId);
    
    @Modifying
    @Transactional
    @Query("""
        DELETE FROM EmployeeManagerHistory h WHERE h.employee.employeeId=:employeeId
    """)
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}
