package com.example.time.controller;

import com.example.time.dto.ShiftDTO;
import com.example.time.entity.Shift;
import com.example.time.services.ShiftService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hrms/time/shifts")
@AllArgsConstructor
public class ShiftController {

    private ShiftService shiftService;

    /**
     * CREATE SHIFT
     * HR_OPERATIONS, ADMIN only
     */
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PostMapping
    public Shift createShift(@RequestBody ShiftDTO dto) {
        Shift shift = new Shift();
        shift.setShiftName(dto.getShiftName());
        shift.setStartTime(dto.getStartTime());
        shift.setEndTime(dto.getEndTime());
        return shiftService.createShift(shift);
    }

    /**
     * VIEW SHIFTS
     * All authenticated users
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<Shift> getAllShifts() {
        return shiftService.getAllShifts();
    }
}
