package com.example.time.controller;

import com.example.time.dto.AttendanceDTO;
import com.example.time.mapper.AttendanceMapper;
import com.example.time.services.AttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hrms/time/attendance")
@AllArgsConstructor
public class AttendanceController {

    private AttendanceService attendanceService;

    /**
     * POST /api/v1/hrms/time/attendance/check-in
     */
    @PreAuthorize("hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId)")
    @PostMapping("/check-in")
    public AttendanceDTO checkIn(@RequestParam long employeeId) {
        return AttendanceMapper.toDTO(attendanceService.checkIn(employeeId));
    }

    /**
     * POST /api/v1/hrms/time/attendance/check-out
     */
    @PreAuthorize("hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId)")
    @PostMapping("/check-out")
    public AttendanceDTO checkOut(@RequestParam long employeeId) {
        return AttendanceMapper.toDTO(attendanceService.checkOut(employeeId));
    }

    /**
     * GET /api/v1/hrms/time/attendance/{employeeId}
     */
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)

    @GetMapping("/{employeeId}")
    public List<AttendanceDTO> getAttendance(@PathVariable long employeeId) {
        return attendanceService.getEmployeeAttendance(employeeId)
                .stream()
                .map(AttendanceMapper::toDTO)
                .toList();
    }
}
