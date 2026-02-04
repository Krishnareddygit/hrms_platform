package com.example.time.controller;

import com.example.time.dto.HolidayDTO;
import com.example.time.entity.Holiday;
import com.example.time.services.HolidayService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hrms/time/holidays")
@AllArgsConstructor
public class HolidayController {

    private HolidayService holidayService;

    /**
     * CREATE HOLIDAY
     * HR_OPERATIONS, ADMIN only
     */
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @PostMapping
    public Holiday createHoliday(@RequestBody HolidayDTO dto) {
        Holiday holiday = new Holiday();
        holiday.setHolidayDate(dto.getHolidayDate());
        holiday.setOccasion(dto.getOccasion());
        holiday.setLocation(dto.getLocation());
        holiday.setOptional(dto.isOptional());
        return holidayService.createHoliday(holiday);
    }

    /**
     * VIEW HOLIDAYS BY LOCATION
     * All authenticated users
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/location/{location}")
    public List<Holiday> getByLocation(@PathVariable String location) {
        return holidayService.getHolidaysByLocation(location);
    }
}
