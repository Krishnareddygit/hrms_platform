package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.EmploymentContract;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmploymentContractRepository extends JpaRepository<EmploymentContract, Long> {

    @Query("""
        SELECT c FROM EmploymentContract c
        WHERE c.employee.employeeId = :id
    """)
    List<EmploymentContract> findByEmployeeId(@Param(("id")) Long id);

    @Modifying
    @Transactional
    @Query("""
    DELETE FROM
        EmploymentContract ec
            WHERE ec.employee.employeeId=:id
    """)
    void deleteByEmployeeId(@Param(("id")) Long id);
}
