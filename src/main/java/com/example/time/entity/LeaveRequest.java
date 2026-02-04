package com.example.time.entity;

import com.example.EmployeeManagement.Model.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "leave_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private Long leaveTypeId;

    private LocalDate fromDate;
    private LocalDate toDate;

    private Integer totalDays;

    @Column(nullable = false)
    private String status; // PENDING, APPROVED, REJECTED

    private Long approvedBy;

    private LocalDateTime approvedOn;

    private LocalDateTime appliedOn;
}
