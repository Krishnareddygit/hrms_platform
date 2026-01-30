package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Model.JobDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobDetailsRepository extends JpaRepository<JobDetails, Long> {

    @Query("""
        SELECT j FROM JobDetails j
        WHERE j.employee.employeeId = :id
    """)
    Optional<JobDetails> findByEmployeeId(@Param(("id")) Long id);


    @Modifying
    @Transactional
    @Query("""
        DELETE FROM JobDetails jd
            WHERE jd.employee.employeeId=:id
    """)
    void deleteByEmployeeId(@Param(("id")) Long id);

}
