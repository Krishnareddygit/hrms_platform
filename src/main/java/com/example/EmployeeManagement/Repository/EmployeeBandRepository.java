package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.EmployeeBand;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeBandRepository extends JpaRepository<EmployeeBand, Long> {

    @Query("""
        SELECT eb FROM EmployeeBand eb
        WHERE eb.employee.employeeId = :id
        ORDER BY eb.fromDate DESC
    """)
    List<EmployeeBand> findByEmployeeId(@Param(("id")) Long id);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM EmployeeBand eb
            WHERE eb.employee.employeeId=:id
    """)
    void deleteByEmployeeId(@Param(("id")) Long id);
}
