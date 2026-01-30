package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.EmployeeAddress;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress , Long> {

    @Query("""
            SELECT ea
            FROM EmployeeAddress ea
            WHERE ea.employee.employeeId=:id
    """)
    public List<EmployeeAddress> findByEmployeeId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM
            EmployeeAddress ae
            WHERE ae.employee.employeeId=:id
    """)
    public void deleteByEmployeeId(@Param("id") Long id);
}
