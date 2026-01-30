package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.EmployeeSkill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {

    @Query("""
        SELECT es
        FROM EmployeeSkill es
        WHERE es.employee.employeeId = :id
    """)
    List<EmployeeSkill> findByEmployeeId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM
        EmployeeSkill es
        WHERE es.employee.employeeId=:id
    """)
    void deleteByEmployeeId(@Param("id") Long id);
}

