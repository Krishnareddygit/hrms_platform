package com.example.time.controller;

import com.example.EmployeeManagement.Model.Employee;
import com.example.time.dto.LeaveRequestDTO;
import com.example.time.entity.LeaveRequest;
import com.example.time.mapper.LeaveRequestMapper;
import com.example.time.services.LeaveService;
import com.example.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hrms/time/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;
    private final SecurityUtil securityUtil;

    /**
     * APPLY LEAVE
     * Employee can apply ONLY for self
     */
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/apply")
    public LeaveRequestDTO applyLeave(@RequestBody LeaveRequestDTO dto) {

        // Logged-in employee (SAFE)
        Employee employee = securityUtil.getLoggedInEmployee();

        LeaveRequest entity = LeaveRequestMapper.toEntity(dto, employee);
        LeaveRequest saved = leaveService.applyLeave(entity);

        return LeaveRequestMapper.toDTO(saved);
    }

    /**
     * APPROVE LEAVE
     * ONLY employee's CURRENT MANAGER can approve
     */
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/{leaveRequestId}/approve")
    public LeaveRequestDTO approveLeave(@PathVariable Long leaveRequestId) {

        Employee approver = securityUtil.getLoggedInEmployee();

        LeaveRequest approved =
                leaveService.approveLeave(leaveRequestId, approver.getEmployeeId());

        return LeaveRequestMapper.toDTO(approved);
    }

    /**
     * REJECT LEAVE
     * ONLY employee's CURRENT MANAGER can reject
     */
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/{leaveRequestId}/reject")
    public LeaveRequestDTO rejectLeave(@PathVariable Long leaveRequestId) {

        Employee approver = securityUtil.getLoggedInEmployee();

        LeaveRequest rejected =
                leaveService.rejectLeave(leaveRequestId, approver.getEmployeeId());

        return LeaveRequestMapper.toDTO(rejected);
    }
}
