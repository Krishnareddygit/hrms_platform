package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.EmployeeEmergency;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeEmergencyRepository extends JpaRepository<EmployeeEmergency, Long> {

    @Query("""
        SELECT ee
            FROM EmployeeEmergency ee
                WHERE ee.employee.employeeId=:id
    """)
    public List<EmployeeEmergency> findByEmployeeId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM
            EmployeeEmergency ee
                WHERE ee.employee.employeeId=:id
    """)
    public void deleteByEmployeeID(@Param("id") Long id);
}
