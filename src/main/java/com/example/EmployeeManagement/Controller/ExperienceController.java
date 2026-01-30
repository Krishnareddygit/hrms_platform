package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.ExperienceDTO;
import com.example.EmployeeManagement.Model.Experience;
import com.example.EmployeeManagement.Service.ExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    // Get all experience
    @GetMapping("/experience")
    public ResponseEntity<List<ExperienceDTO>> getAllExperience() {
        return ResponseEntity.ok(experienceService.getAllExperience());
    }

    // Get experience by ID
    @GetMapping("/experience/{experienceId}")
    public ResponseEntity<ExperienceDTO> getById(@PathVariable Long experienceId) {
        return ResponseEntity.ok(experienceService.getExperienceById(experienceId));
    }

    // Get all experience of employee
    @GetMapping("/{employeeId}/experience")
    public ResponseEntity<List<ExperienceDTO>> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(experienceService.getExperienceByEmployeeId(employeeId));
    }

    // Add experience
    @PostMapping("/{employeeId}/experience")
    public ResponseEntity<ExperienceDTO> addExperience(
            @PathVariable Long employeeId,
            @RequestBody Experience experience) {

        return ResponseEntity.ok(experienceService.addExperience(employeeId, experience));
    }

    // Update experience
    @PutMapping("/{employeeId}/experience/{experienceId}")
    public ResponseEntity<ExperienceDTO> updateExperience(
            @PathVariable Long employeeId,
            @PathVariable Long experienceId,
            @RequestBody ExperienceDTO dto) {

        return ResponseEntity.ok(
                experienceService.updateExperience(experienceId, employeeId, dto)
        );
    }

    // Delete one experience
    @DeleteMapping("/experience/{experienceId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long experienceId) {
        experienceService.deleteByExperienceId(experienceId);
        return ResponseEntity.noContent().build();
    }

    // Delete all experience of employee
    @DeleteMapping("/{employeeId}/experience")
    public ResponseEntity<Void> deleteByEmployee(@PathVariable Long employeeId) {
        experienceService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
