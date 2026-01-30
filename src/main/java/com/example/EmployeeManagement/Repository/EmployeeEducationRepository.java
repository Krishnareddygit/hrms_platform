package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.EmployeeEducation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeEducationRepository extends JpaRepository<EmployeeEducation , Long> {

    @Query("""
            SELECT ee
            FROM EmployeeEducation ee
            WHERE ee.employee.employeeId=:id
    """)
    public List<EmployeeEducation> findByEmployeeId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
            DELETE
            FROM EmployeeEducation ee
            WHERE ee.employee.employeeId=:id
    """)
    public void deleteByEmployeeId(@Param("id") Long id);
}
