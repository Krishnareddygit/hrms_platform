package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.Experience;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("""
        SELECT e FROM Experience e
        WHERE e.employee.employeeId = :employeeId
    """)
    List<Experience> findByEmployeeId(@Param(("id")) Long id);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Experience e
            WHERE e.employee.employeeId=:id
    """)
    void deleteByEmployeeId(@Param(("id")) Long id);
}
