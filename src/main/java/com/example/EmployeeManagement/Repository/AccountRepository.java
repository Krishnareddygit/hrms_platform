package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
        SELECT a FROM Account a
        WHERE a.employee.employeeId = :id
    """)
    Optional<Account> findByEmployeeId(@Param(("id")) Long id);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Account a WHERE a.employee.employeeId=:id
    """)
    void deleteByEmployeeId(@Param(("id")) Long id);
}
